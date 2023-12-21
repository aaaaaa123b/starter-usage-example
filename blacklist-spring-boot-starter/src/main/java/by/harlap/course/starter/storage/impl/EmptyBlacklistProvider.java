package by.harlap.course.starter.storage.impl;

import by.harlap.course.starter.storage.PredefinedBlacklistProvider;

import java.util.ArrayList;
import java.util.List;

public class EmptyBlacklistProvider implements PredefinedBlacklistProvider {

    @Override
    public List<String> getBlockedUsernames() {
        return new ArrayList<>();
    }
}
