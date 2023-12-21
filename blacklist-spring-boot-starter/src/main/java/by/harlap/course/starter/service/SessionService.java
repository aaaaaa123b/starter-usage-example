package by.harlap.course.starter.service;

import by.harlap.course.starter.model.SessionHolder;

import java.util.List;

public interface SessionService {

    SessionHolder createSessionByUsername(String username);

    SessionHolder getCurrentSession();

    SessionHolder getSessionByUsername(String username);

    List<SessionHolder> getAllSessions();
}
