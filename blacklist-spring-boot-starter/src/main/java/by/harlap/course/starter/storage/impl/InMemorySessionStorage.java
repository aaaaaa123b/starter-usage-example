package by.harlap.course.starter.storage.impl;

import by.harlap.course.starter.model.SessionHolder;
import by.harlap.course.starter.storage.SessionStorage;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InMemorySessionStorage implements SessionStorage {

    private final Map<String, SessionHolder> sessionHolders = new ConcurrentHashMap<>();

    @Override
    public void save(SessionHolder sessionHolder) {
        sessionHolders.put(sessionHolder.getSessionID(), sessionHolder);
    }

    @Override
    public void removeByID(String sessionID) {
        sessionHolders.remove(sessionID);
    }

    @Override
    public SessionHolder getByID(String sessionID) {
        return sessionHolders.get(sessionID);
    }

    @Override
    public SessionHolder getByUsername(String username) {
        return sessionHolders.values().stream()
                .filter(sessionHolder -> sessionHolder.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<SessionHolder> getAll() {
        return sessionHolders.values().stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean containsID(String sessionID) {
        return sessionHolders.containsKey(sessionID);
    }
}
