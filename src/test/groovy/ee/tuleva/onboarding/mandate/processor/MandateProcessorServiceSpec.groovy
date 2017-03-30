package ee.tuleva.onboarding.mandate.processor

import ee.tuleva.onboarding.auth.UserFixture
import ee.tuleva.onboarding.auth.session.GenericSessionStore
import ee.tuleva.onboarding.mandate.Mandate
import ee.tuleva.onboarding.mandate.MandateFixture
import ee.tuleva.onboarding.mandate.content.MandateXmlMessage
import ee.tuleva.onboarding.mandate.content.MandateXmlService
import ee.tuleva.onboarding.user.User
import org.springframework.jms.core.JmsTemplate
import spock.lang.Specification

class MandateProcessorServiceSpec extends Specification {

    private MandateXmlService mandateXmlService = Mock(MandateXmlService)
    private JmsTemplate jmsTemplate = Mock(JmsTemplate)
    private GenericSessionStore genericSessionStore = Mock(GenericSessionStore);
    private MandateProcessorService service = new MandateProcessorService(mandateXmlService, jmsTemplate, genericSessionStore)

    User sampleUser = UserFixture.sampleUser()
    Mandate sampleMandate = MandateFixture.sampleMandate()

    def "Start"() {
        given:
        1 * mandateXmlService.getRequestContents(sampleUser, sampleMandate.id) >> sampleMessages
        2 * jmsTemplate.send("MHUB.PRIVATE.IN", _)

        when:
        List<MandateXmlMessage> messages = service.start(sampleUser, sampleMandate)
        then:
        true
    }

    def "IsFinished"() {

    }

    List<String> sampleMessages = [
            MandateXmlMessage.builder().id("123").message("message").build(),
            MandateXmlMessage.builder().id("124").message("message").build()
    ]
}