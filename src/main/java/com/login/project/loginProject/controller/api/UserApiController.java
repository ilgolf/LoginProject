package com.login.project.loginProject.controller.api;

import com.login.project.loginProject.model.Member;
import com.login.project.loginProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Member member, HttpServletRequest request,
                        HttpServletResponse response, RedirectAttributes attributes) {
        HttpSession session = request.getSession();
        member = userService.login(member);

        if (member == null) {
            throw new IllegalStateException("아직 회원 가입 안하셨습니다.");
        }

        return "redirect:/";
    }

    @PostMapping("/join")
    public String save(@RequestBody Member member) {
        Member user = Member.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();

        userService.join(user);

        return "redirect:/";
    }
}
