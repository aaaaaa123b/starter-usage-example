package by.harlap.course.starter.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.sessions")
@NoArgsConstructor
@Getter
@Setter
public class SessionManagementProperties {

    private String[] blacklist;

    private boolean scheduleInvalidation;
}
