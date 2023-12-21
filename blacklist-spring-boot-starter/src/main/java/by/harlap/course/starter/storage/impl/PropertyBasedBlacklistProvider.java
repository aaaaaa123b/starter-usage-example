package by.harlap.course.starter.storage.impl;

import by.harlap.course.starter.property.SessionManagementProperties;
import by.harlap.course.starter.storage.PredefinedBlacklistProvider;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class PropertyBasedBlacklistProvider implements PredefinedBlacklistProvider {

    private final SessionManagementProperties sessionManagementProperties;

    @Override
    public List<String> getBlockedUsernames() {
        return Arrays.asList(sessionManagementProperties.getBlacklist());
    }
}
