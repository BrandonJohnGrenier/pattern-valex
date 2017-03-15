package fm.pattern.valex.config;

import org.junit.Test;

import fm.pattern.valex.PatternAssertions;
import fm.pattern.valex.config.ValexConfigurationFactory;
import fm.pattern.valex.config.ValexPropertiesConfiguration;
import fm.pattern.valex.config.ValexYamlConfiguration;

public class ValexConfigurationFactoryTest {

    @Test
    public void shouldBeAbleToExplicitlySetTheValidationRepositoryToUse() {
        ValexConfigurationFactory.use(new ValexYamlConfiguration());
        PatternAssertions.assertThat(ValexConfigurationFactory.getConfiguration().getClass()).isEqualTo(ValexYamlConfiguration.class);

        ValexConfigurationFactory.use(null);
        PatternAssertions.assertThat(ValexConfigurationFactory.getConfiguration().getClass()).isEqualTo(ValexPropertiesConfiguration.class);
    }

    @Test
    public void theClassShouldBeAWellDefinedUtilityClass() {
        PatternAssertions.assertClass(ValexConfigurationFactory.class).isAWellDefinedUtilityClass();
    }

}
