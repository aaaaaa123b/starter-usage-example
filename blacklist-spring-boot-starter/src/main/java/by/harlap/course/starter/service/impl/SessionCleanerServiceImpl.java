package by.harlap.course.starter.service.impl;

import by.harlap.course.starter.model.SessionHolder;
import by.harlap.course.starter.property.SessionManagementProperties;
import by.harlap.course.starter.service.SessionCleanerService;
import by.harlap.course.starter.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Log4j2
public class SessionCleanerServiceImpl implements SessionCleanerService {

    private final SessionService sessionService;
    private final SessionManagementProperties properties;

    @Scheduled(cron = "@midnight")
    public synchronized void invalidate() {
        if (properties.isScheduleInvalidation()) {
            sessionService.getAllSessions().forEach(this::invalidate);
        } else {
            log.warn("Scheduled midnight invalidation for sessions but this functionality has been disabled");
        }
    }

    private void invalidate(SessionHolder sessionHolder) {
        sessionHolder.getSession().invalidate();
    }
}
