package ng.com.ninepsb.nibss_nip_lib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NipConfigurationCustomizer {

    /**
     * Default no-op customizer bean.
     */
    @Bean
    public NipCustomizer defaultNipCustomizer() {
        return properties -> {
            // Default implementation - no customization
        };
    }

    /**
     * Apply all customizers to the configuration properties.
     *
     * @param properties The NIP configuration properties
     * @param customizers Array of customizers to apply
     */
    public static void applyCustomizers(NipProperties properties,
                                        NipCustomizer... customizers) {
        if (customizers != null) {
            for (NipCustomizer customizer : customizers) {
                customizer.customize(properties);
            }
        }
    }

    /**
     * Interface for customizing NIP configuration during application startup.
     */
    @FunctionalInterface
    public interface NipCustomizer {
        /**
         * Customize the NIP configuration properties.
         *
         * @param properties The NIP configuration properties to customize
         */
        void customize(NipProperties properties);
    }
}
