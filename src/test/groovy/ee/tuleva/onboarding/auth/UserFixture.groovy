package ee.tuleva.onboarding.auth

import ee.tuleva.onboarding.user.MemberFixture
import ee.tuleva.onboarding.user.User
import ee.tuleva.onboarding.user.preferences.UserPreferences

import java.time.Instant

public class UserFixture {

    public static User.UserBuilder sampleUser() {
        return User.builder()
                .id(999)
                .firstName("Jordan")
                .lastName("Valdma")
                .personalCode("38812121215")
                .email("jordan.valdma@gmail.com")
                .phoneNumber("5555555")
                .createdDate(Instant.parse("2017-01-31T10:06:01Z"))
                .updatedDate(Instant.parse("2017-01-31T10:06:01Z"))
                .active(true)
                .member(MemberFixture.sampleMember)
    }

    public static User.UserBuilder sampleUserNonMember() {
        return User.builder()
                .id(999)
                .firstName("Jordan")
                .lastName("Valdma")
                .personalCode("38812121215")
                .email("jordan.valdma@gmail.com")
                .phoneNumber("5555555")
                .createdDate(Instant.parse("2017-01-31T10:06:01Z"))
                .updatedDate(Instant.parse("2017-01-31T10:06:01Z"))
                .active(true)
                .member(null)
    }

    public static UserPreferences sampleUserPreferences() {
        return UserPreferences.builder()
                .addressRow1("Tatari 19-17")
                .addressRow2("TALLINN")
                .addressRow3("TALLINN")
                .country("EE")
                .postalIndex("12345")
                .districtCode("0784")
                .contactPreference(UserPreferences.ContactPreferenceType.E)
                .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                .noticeNeeded(1)
                .build();
    }


    public static List<UserPreferences> userPreferencesWithContactPreferencesPartiallyEmpty() {
        return [
                UserPreferences.builder()
                        .addressRow1("Tatari 19-17")
                        .addressRow2("TALLINN")
                        .addressRow3("TALLINN")
                        .country("EE")
                        .postalIndex("12345")
                        .districtCode("0784")
                        .contactPreference(null)
                        .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                        .noticeNeeded(1)
                        .build(),
                UserPreferences.builder()
                        .addressRow1("Tatari 19-17")
                        .addressRow2("TALLINN")
                        .addressRow3("TALLINN")
                        .country("EE")
                        .postalIndex("12345")
                        .districtCode("0784")
                        .contactPreference(UserPreferences.ContactPreferenceType.E)
                        .languagePreference(null)
                        .noticeNeeded(1)
                        .build(),
                UserPreferences.builder()
                        .addressRow1("Tatari 19-17")
                        .addressRow2("TALLINN")
                        .addressRow3("TALLINN")
                        .country("EE")
                        .postalIndex("12345")
                        .districtCode("0784")
                        .contactPreference(UserPreferences.ContactPreferenceType.E)
                        .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                        .noticeNeeded(null)
                        .build()
        ]
    }
    public static List<UserPreferences> userPreferencesWithAddresPartiallyEmpty() {
        return [
            UserPreferences.builder()
                .addressRow1("")
                .addressRow2("TALLINN")
                .addressRow3("TALLINN")
                .country("EE")
                .postalIndex("12345")
                .districtCode("0784")
                .contactPreference(UserPreferences.ContactPreferenceType.E)
                .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                .noticeNeeded(1)
                .build(),
            UserPreferences.builder()
                    .addressRow1("Tatari 19-17")
                    .addressRow2("")
                    .addressRow3("TALLINN")
                    .country("EE")
                    .postalIndex("12345")
                    .districtCode("0784")
                    .contactPreference(UserPreferences.ContactPreferenceType.E)
                    .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                    .noticeNeeded(1)
                    .build(),
            UserPreferences.builder()
                    .addressRow1("Tatari 19-17")
                    .addressRow2("TALLINN")
                    .addressRow3("")
                    .country("EE")
                    .postalIndex("12345")
                    .districtCode("0784")
                    .contactPreference(UserPreferences.ContactPreferenceType.E)
                    .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                    .noticeNeeded(1)
                    .build(),
            UserPreferences.builder()
                    .addressRow1("Tatari 19-17")
                    .addressRow2("TALLINN")
                    .addressRow3("TALLINN")
                    .country("")
                    .postalIndex("12345")
                    .districtCode("0784")
                    .contactPreference(UserPreferences.ContactPreferenceType.E)
                    .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                    .noticeNeeded(1)
                    .build(),
            UserPreferences.builder()
                    .addressRow1("Tatari 19-17")
                    .addressRow2("TALLINN")
                    .addressRow3("TALLINN")
                    .country("EE")
                    .postalIndex("")
                    .districtCode("0784")
                    .contactPreference(UserPreferences.ContactPreferenceType.E)
                    .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                    .noticeNeeded(1)
                    .build(),
            UserPreferences.builder()
                    .addressRow1("Tatari 19-17")
                    .addressRow2("TALLINN")
                    .addressRow3("TALLINN")
                    .country("EE")
                    .postalIndex("12345")
                    .districtCode("")
                    .contactPreference(UserPreferences.ContactPreferenceType.E)
                    .languagePreference(UserPreferences.LanguagePreferenceType.EST)
                    .noticeNeeded(1)
                    .build(),
                ]
    }

}
