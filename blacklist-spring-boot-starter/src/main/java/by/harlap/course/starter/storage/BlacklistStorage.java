package by.harlap.course.starter.storage;

import java.util.Set;

public interface BlacklistStorage {

    boolean containsUsername(String username);

    Set<String> getUsernames();
}
