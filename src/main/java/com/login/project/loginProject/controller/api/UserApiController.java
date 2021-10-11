package com.login.project.loginProject.controller.api;

import com.login.project.loginProject.dto.MemberDTO;
import com.login.project.loginProject.model.Member;
import com.login.project.loginProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/login")
    public MemberDTO<Integer> login(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Member member1 = userService.login(member);
        String username = member1.getUsername();

        session.setAttribute(username, member1);

        return new MemberDTO<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/join")
    public MemberDTO<Integer> save(@RequestBody Member member) {
        Member user = Member.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .email(member.getEmail())
                .build();

        userService.join(user);

        return new MemberDTO<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/update")
    public MemberDTO<Integer> update(@RequestBody Member member, HttpServletRequest request) {
        HttpSession session = request.getSession();
        userService.modify(member);

        session.setAttribute(member.getUsername(), member);

        return new MemberDTO<>(HttpStatus.OK.value(), 1);
    }
}