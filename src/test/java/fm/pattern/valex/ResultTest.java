package fm.pattern.valex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import fm.pattern.valex.config.PropertyConfigurationFile;
import fm.pattern.valex.config.ValexConfiguration;

public class ResultTest {

    @Before
    public void before() {
        ValexConfiguration.use(new PropertyConfigurationFile());
    }

    @Test
    public void shouldBeAbleToSubstituteArgumentsInAnErrorMessage() {
        Result<String> result = Result.reject("The username %s cannot be greater than %d characters.", "smithers", 5);
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("The username smithers cannot be greater than 5 characters.");
    }

    @Test
    public void shouldBeAbleToResolveACodeAndMessageForAnErrorKey() {
        Result<String> result = Result.reject("contact.name.required");
        assertThat(result.getErrors().get(0).getCode()).isEqualTo("CON-1000");
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("A contact name is required.");
    }

    @Test
    public void shouldBeAbleToResolveACodeAndMessageAndSubstituteArgumentsForAnErrorKey() {
        Result<String> result = Result.reject("username.length", "jim", 2);
        assertThat(result.getErrors().get(0).getCode()).isEqualTo("LOC-NTFD");
        assertThat(result.getErrors().get(0).getMessage()).isEqualTo("The username jim cannot be greater than 2 characters.");
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldBeAbleToThrowAnExceptionWhenTheResultHasErrors() {
        Result<String> result = Result.reject("contact.name.required");
        result.orThrow();
    }

    @Test(expected = ResourceConflictException.class)
    public void shouldBeAbleToThrowACustomisedExceptionWhenTheResultHasErrors() {
        Result<String> result = Result.reject("contact.name.required");
        result.orThrow(ResourceConflictException.class);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void shouldBeAbleToThrowTheUnderlyingExceptionBoundToTheKey() {
        Result<String> result = Result.reject("contact.name.required");
        result.doThrow();
    }

    @Test
    public void shouldNotThrowACustomizedExceptionAndReturnTheInstanceWhenTheResultHasNoErrors() {
        Result<String> result = Result.accept("string");
        assertThat(result.orThrow(BadRequestException.class)).isEqualTo("string");
    }

    @Test
    public void shouldNotThrowAnExceptionAndReturnTheInstanceWhenTheResultHasNoErrors() {
        Result<String> result = Result.accept("string");
        assertThat(result.orThrow()).isEqualTo("string");
    }

}
