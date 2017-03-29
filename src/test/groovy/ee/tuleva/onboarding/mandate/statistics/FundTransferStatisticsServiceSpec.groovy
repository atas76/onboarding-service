package ee.tuleva.onboarding.mandate.statistics

import ee.tuleva.onboarding.mandate.MandateFixture
import spock.lang.Specification

class FundTransferStatisticsServiceSpec extends Specification {

    FundTransferStatisticsRepository fundTransferStatisticsRepository = Mock(FundTransferStatisticsRepository)

    FundTransferStatisticsService service = new FundTransferStatisticsService(fundTransferStatisticsRepository)

    def "AddFrom: Add value from Mandate and FundValueStatistics"() {
        given:

        int callCount = MandateFixture.sampleMandate().fundTransferExchanges.size()

        callCount * fundTransferStatisticsRepository
                .findOneByIsin(_ as String) >> sampleFundTransferStatistics()

        BigDecimal firstValue = sampleFundTransferStatistics().value + FundValueStatisticsFixture.sampleFundValueStatisticsList().get(0).value
        BigDecimal secondValue = firstValue + FundValueStatisticsFixture.sampleFundValueStatisticsList().get(1).value
        BigDecimal thirdValue = secondValue + FundValueStatisticsFixture.sampleFundValueStatisticsList().get(2).value

        BigDecimal firstAmount = sampleFundTransferStatistics().amount + MandateFixture.sampleMandate().fundTransferExchanges.get(0).getAmount()
        BigDecimal secondAmount = firstAmount + MandateFixture.sampleMandate().fundTransferExchanges.get(1).getAmount()
        BigDecimal thirdAmount = secondAmount + MandateFixture.sampleMandate().fundTransferExchanges.get(2).getAmount()

        when:
        service.addFrom(MandateFixture.sampleMandate(), FundValueStatisticsFixture.sampleFundValueStatisticsList())

        then:

        1 * fundTransferStatisticsRepository.save({FundTransferStatistics fundTransferStatistics ->
            fundTransferStatistics.value == firstValue && fundTransferStatistics.amount == firstAmount
        })

        1 * fundTransferStatisticsRepository.save({FundTransferStatistics fundTransferStatistics ->
            fundTransferStatistics.value == secondValue && fundTransferStatistics.amount == secondAmount
        })

        1 * fundTransferStatisticsRepository.save({FundTransferStatistics fundTransferStatistics ->
            fundTransferStatistics.value == thirdValue && fundTransferStatistics.amount == thirdAmount
        })
    }

    def "AddFrom: Create new fund transfer stats for isin if none existing"() {
        given:

        int callCount = MandateFixture.sampleMandate().fundTransferExchanges.size()

        callCount * fundTransferStatisticsRepository
                .findOneByIsin(_ as String) >> null

        BigDecimal firstValue = BigDecimal.ZERO + FundValueStatisticsFixture.sampleFundValueStatisticsList().get(0).value
        BigDecimal firstAmount = BigDecimal.ZERO + MandateFixture.sampleMandate().fundTransferExchanges.get(0).getAmount()

        when:
        service.addFrom(MandateFixture.sampleMandate(), FundValueStatisticsFixture.sampleFundValueStatisticsList())

        then:

        1 * fundTransferStatisticsRepository.save({FundTransferStatistics fundTransferStatistics ->
            fundTransferStatistics.value == firstValue && fundTransferStatistics.amount == firstAmount
        })

    }

    FundTransferStatistics sampleFundTransferStatistics() {
        return FundTransferStatistics.builder()
                .isin(MandateFixture.sampleMandate().fundTransferExchanges.get(0).sourceFundIsin)
                .value(20000)
                .amount(2)
                .build()
    }
}
