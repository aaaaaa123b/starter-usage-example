package by.harlap.course.starter.service.impl;

import by.harlap.course.starter.exception.SessionNotFoundException;
import by.harlap.course.starter.exception.UserBlockedException;
import by.harlap.course.starter.model.SessionHolder;
import by.harlap.course.starter.service.BlacklistService;
import by.harlap.course.starter.service.SessionService;
import by.harlap.course.starter.storage.SessionStorage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultSessionService implements SessionService {

    private final BlacklistService blacklistService;
    private final SessionStorage sessionStorage;

    @Override
    public SessionHolder createSessionByUsername(String username) {
        if (blacklistService.containsUsername(username)) {
            throw new UserBlockedException(username);
        }

        final HttpSession session = getOrCreateHttpSession();
        final SessionHolder sessionHolder = new SessionHolder(username, session);

        sessionStorage.save(sessionHolder);

        return sessionHolder;
    }

    @Override
    public SessionHolder getCurrentSession() {
        return Optional.ofNullable(getHttpSession())
                .map(session -> sessionStorage.getByID(session.getId()))
                .orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public SessionHolder getSessionByUsername(String username) {
        return Optional.ofNullable(sessionStorage.getByUsername(username))
                .orElseThrow(SessionNotFoundException::new);
    }

    @Override
    public List<SessionHolder> getAllSessions() {
        return Collections.unmodifiableList(sessionStorage.getAll());
    }

    protected HttpSession getOrCreateHttpSession() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getSession(true);
    }

    protected HttpSession getHttpSession() {
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest().getSession(false);
    }
}
