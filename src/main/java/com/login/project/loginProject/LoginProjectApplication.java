package com.login.project.loginProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class LoginProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginProjectApplication.class, args);
    }
}
