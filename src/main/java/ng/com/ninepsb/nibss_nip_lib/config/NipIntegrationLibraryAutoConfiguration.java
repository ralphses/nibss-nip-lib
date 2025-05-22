package ng.com.ninepsb.nibss_nip_lib.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.exception.NipClientException;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;
import ng.com.ninepsb.nibss_nip_lib.util.NipRequestRegister;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.List;
import java.util.Set;

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

    @Bean
    @Lazy  // Only create when actually needed
    NipRequestRegister nipRequestRegisterCached() {

        // Use ConcurrentHashMap for thread safety if needed
        NipRequestRegister requestRegister = new NipRequestRegister();

        // Consider using Spring's classpath scanning with caching
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        // More specific filter to reduce false positives
        scanner.addIncludeFilter(new AnnotationTypeFilter(NipRequestItem.class));
        scanner.setResourcePattern("**/*.class"); // Be explicit about what we're scanning

        String basePackage = "ng.com.ninepsb.nibss_nip_lib.annotations";

        // Collect all candidates first, then process (better for error handling)
        Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);

        for (BeanDefinition bd : candidates) {
            processCandidate(bd, requestRegister);
        }

        return requestRegister;
    }

    private void processCandidate(BeanDefinition bd, NipRequestRegister requestRegister) {
        try {
            Class<?> clazz = Class.forName(bd.getBeanClassName());
            NipRequestItem annotation = clazz.getAnnotation(NipRequestItem.class);

            if (annotation != null) {
                requestRegister.register(annotation.request(), annotation.response());
            }
        } catch (ClassNotFoundException e) {
            // Log warning instead of failing fast - more resilient
            log.warn("Could not load class for NIP registration: {}", bd.getBeanClassName(), e);
        }
    }
}
