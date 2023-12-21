package by.harlap.course.starter.service.impl;

import by.harlap.course.starter.service.BlacklistService;
import by.harlap.course.starter.storage.BlacklistStorage;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class DefaultBlacklistService implements BlacklistService {

    private final BlacklistStorage blacklistStorage;

    @Override
    public boolean containsUsername(String username) {
        return blacklistStorage.containsUsername(username);
    }

    @Override
    public Set<String> getUsernames() {
        return Collections.unmodifiableSet(blacklistStorage.getUsernames());
    }
}
