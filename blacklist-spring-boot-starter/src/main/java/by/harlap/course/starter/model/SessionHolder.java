package by.harlap.course.starter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
public class SessionHolder {

    public SessionHolder(String username, HttpSession session) {
        this.username = username;
        this.sessionID = session.getId();
        this.started = Instant.ofEpochMilli(session.getCreationTime());

        this.session = session;
    }

    private final String username;
    private final String sessionID;
    private final Instant started;

    @JsonIgnore
    @ToString.Exclude
    private final HttpSession session;
}
