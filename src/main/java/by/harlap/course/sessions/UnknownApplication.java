package by.harlap.course.sessions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "by.harlap.course.starter.config",
    "by.harlap.course.sessions"
})
public class UnknownApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnknownApplication.class, args);
    }
}
