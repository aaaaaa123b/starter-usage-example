package by.harlap.course.sessions.controller;

import by.harlap.course.sessions.service.ExampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/example")
    public String generateRandomString() {
        return exampleService.generateRandomString(null);
    }
}
