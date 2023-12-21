package by.harlap.course.starter.service;

import java.util.Set;

public interface BlacklistService {

    boolean containsUsername(String username);

    Set<String> getUsernames();
}
