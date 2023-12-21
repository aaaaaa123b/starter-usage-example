package by.harlap.course.starter.storage.impl;

import by.harlap.course.starter.storage.BlacklistStorage;
import by.harlap.course.starter.storage.PredefinedBlacklistProvider;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBlacklistStorage implements BlacklistStorage {

    private final Set<String> users = ConcurrentHashMap.newKeySet();

    public InMemoryBlacklistStorage(PredefinedBlacklistProvider predefinedBlacklistProvider) {
        users.addAll(predefinedBlacklistProvider.getBlockedUsernames());
    }

    @Override
    public boolean containsUsername(String username) {
        return users.contains(username);
    }

    @Override
    public Set<String> getUsernames() {
        return Collections.unmodifiableSet(users);
    }
}
