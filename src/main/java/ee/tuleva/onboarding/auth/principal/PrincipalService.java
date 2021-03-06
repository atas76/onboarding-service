package ee.tuleva.onboarding.auth.principal;

import ee.tuleva.onboarding.user.User;
import ee.tuleva.onboarding.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalService {

    private final UserRepository userRepository;

    public AuthenticatedPerson getFrom(Person person) {

        User user = userRepository.findByPersonalCode(person.getPersonalCode());

        if(user == null) {
            user = userRepository.save(
                    User.builder()
                            .firstName(WordUtils.capitalizeFully(person.getFirstName()))
                            .lastName(WordUtils.capitalizeFully(person.getLastName()))
                            .personalCode(person.getPersonalCode())
                            .active(true)
                            .build()
            );
        } else if (!user.getActive()) {
            log.info("Failed to login inactive user with personal code {}", person.getPersonalCode());
            throw new InvalidRequestException("INACTIVE_USER");
        }

        return AuthenticatedPerson.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .personalCode(person.getPersonalCode())
                .userId(user.getId())
                .build();

    }

}
