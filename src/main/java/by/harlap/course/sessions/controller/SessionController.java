package by.harlap.course.sessions.controller;

import by.harlap.course.sessions.dto.request.CreateSessionDTO;
import by.harlap.course.starter.model.SessionHolder;
import by.harlap.course.starter.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/session")
    public SessionHolder createSession(@RequestBody CreateSessionDTO dto) {
        return sessionService.createSessionByUsername(dto.getUsername());
    }

    @GetMapping("/session")
    public SessionHolder getSession() {
        return sessionService.getCurrentSession();
    }

    @GetMapping("/session/{username}")
    public SessionHolder getSession(@PathVariable String username) {
        return sessionService.getSessionByUsername(username);
    }
}
