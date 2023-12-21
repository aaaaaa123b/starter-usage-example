package by.harlap.course.starter.config;

import by.harlap.course.starter.bpp.SessionProvidedBeanAnnotationPostProcessor;
import by.harlap.course.starter.listener.ApplicationStartListener;
import by.harlap.course.starter.property.SessionManagementProperties;
import by.harlap.course.starter.service.BlacklistService;
import by.harlap.course.starter.service.SessionCleanerService;
import by.harlap.course.starter.service.SessionService;
import by.harlap.course.starter.service.impl.DefaultBlacklistService;
import by.harlap.course.starter.service.impl.DefaultSessionService;
import by.harlap.course.starter.service.impl.SessionCleanerServiceImpl;
import by.harlap.course.starter.storage.BlacklistStorage;
import by.harlap.course.starter.storage.PredefinedBlacklistProvider;
import by.harlap.course.starter.storage.SessionStorage;
import by.harlap.course.starter.storage.impl.EmptyBlacklistProvider;
import by.harlap.course.starter.storage.impl.InMemoryBlacklistStorage;
import by.harlap.course.starter.storage.impl.InMemorySessionStorage;
import by.harlap.course.starter.storage.impl.PropertyBasedBlacklistProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(SessionManagementProperties.class)
public class SessionManagementAutoConfiguration {

    @Bean
    public ApplicationStartListener applicationStartListener(BlacklistService blacklistService) {
        return new ApplicationStartListener(blacklistService);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    @ConditionalOnBean(SessionManagementProperties.class)
    public PredefinedBlacklistProvider propertyBasedBlacklistProvider(SessionManagementProperties sessionManagementProperties) {
        return new PropertyBasedBlacklistProvider(sessionManagementProperties);
    }

    @Bean
    @ConditionalOnMissingBean({PredefinedBlacklistProvider.class, SessionManagementProperties.class})
    public PredefinedBlacklistProvider emptyBlacklistProvider() {
        return new EmptyBlacklistProvider();
    }

    @Bean
    @ConditionalOnMissingBean(SessionManagementProperties.class)
    public static SessionProvidedBeanAnnotationPostProcessor sessionProvidedBeanAnnotationPostProcessor() {
        return new SessionProvidedBeanAnnotationPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(BlacklistStorage.class)
    public BlacklistStorage inMemoryBlacklistStorage(PredefinedBlacklistProvider predefinedBlacklistProvider) {
        return new InMemoryBlacklistStorage(predefinedBlacklistProvider);
    }

    @Bean
    @ConditionalOnMissingBean(SessionStorage.class)
    public SessionStorage inMemorySessionStorage() {
        return new InMemorySessionStorage();
    }

    @Bean
    @ConditionalOnMissingBean(SessionService.class)
    public SessionService sessionService(BlacklistService blacklistService, SessionStorage sessionStorage) {
        return new DefaultSessionService(blacklistService, sessionStorage);
    }

    @Bean
    @ConditionalOnMissingBean(BlacklistService.class)
    public BlacklistService blacklistService(BlacklistStorage blacklistStorage) {
        return new DefaultBlacklistService(blacklistStorage);
    }

    @Bean
    @ConditionalOnBean(SessionService.class)
    @ConditionalOnMissingBean(SessionCleanerService.class)
    @ConditionalOnProperty("application.sessions.schedule-invalidation")
    public SessionCleanerService sessionCleanerService(SessionService sessionService, SessionManagementProperties sessionManagementProperties) {
        return new SessionCleanerServiceImpl(sessionService, sessionManagementProperties);
    }
}
