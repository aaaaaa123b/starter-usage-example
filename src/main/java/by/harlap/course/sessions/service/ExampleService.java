package by.harlap.course.sessions.service;

import by.harlap.course.starter.annotation.SessionProvided;
import by.harlap.course.starter.model.SessionHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class ExampleService {

    @SessionProvided
    public String generateRandomString(SessionHolder sessionHolder) {
        if (sessionHolder == null) {
            log.warn("Session holder has not been provided");
        } else {
            log.warn("Session holder has been provided: {}", sessionHolder);
        }

        return UUID.randomUUID().toString();
    }
}
