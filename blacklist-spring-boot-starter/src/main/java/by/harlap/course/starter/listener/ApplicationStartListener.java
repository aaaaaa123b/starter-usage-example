package by.harlap.course.starter.listener;

import by.harlap.course.starter.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@RequiredArgsConstructor
@Log4j2
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

    private final BlacklistService blacklistService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent ignored) {
        log.info("[blacklist-spring-boot-starter] starter has been initialized");
        log.info("Blocked users are: {}", blacklistService.getUsernames());
    }
}
