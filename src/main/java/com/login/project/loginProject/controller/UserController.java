package com.login.project.loginProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String joinForm() {
        return "user/joinForm";
    }
}
