package by.harlap.course.starter.storage;

import by.harlap.course.starter.model.SessionHolder;

import java.util.List;

public interface SessionStorage {

    void save(SessionHolder sessionHolder);

    void removeByID(String sessionID);

    SessionHolder getByID(String sessionID);

    SessionHolder getByUsername(String username);

    List<SessionHolder> getAll();

    boolean containsID(String sessionID);
}
