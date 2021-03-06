package ee.tuleva.onboarding.comparisons;


import ee.tuleva.onboarding.auth.principal.AuthenticatedPerson;
import ee.tuleva.onboarding.error.ValidationErrorsException;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ComparisonController {

    private final ComparisonService comparisonService;

    @ResponseBody
    @RequestMapping(path = "comparisons", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ComparisonResponse comparison(@Valid @ModelAttribute @ApiParam ComparisonCommand comparisonCommand,
                            @ApiIgnore @AuthenticationPrincipal AuthenticatedPerson authenticatedPerson,
                            @ApiIgnore Errors errors) throws Exception {
        if (errors != null && errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }

        return comparisonService.compare(comparisonCommand, authenticatedPerson.getUserId());
    }

}
