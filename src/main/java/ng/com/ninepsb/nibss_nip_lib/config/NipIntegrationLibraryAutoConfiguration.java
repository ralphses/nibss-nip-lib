package ng.com.ninepsb.nibss_nip_lib.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(NipProperties.class)
@ComponentScan(basePackages = "ng.com.ninepsb.nibss_nip_lib")
@ConditionalOnProperty(prefix = "nip", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import({NipConfigFactory.class, NipConfigurationCustomizer.class})
public class NipIntegrationLibraryAutoConfiguration {

    private final NipProperties properties;
    private final List<NipConfigurationCustomizer.NipCustomizer> customizers;

    @PostConstruct
    public void init() {
        log.info("Applying {} NIP customizer(s) to properties", customizers.size());
        NipConfigurationCustomizer.applyCustomizers(properties, customizers.toArray(new NipConfigurationCustomizer.NipCustomizer[0]));
    }
}
