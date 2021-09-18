package com.login.project.loginProject.controller.api;

import com.login.project.loginProject.model.Member;
import com.login.project.loginProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    public String login(Member member, HttpSession session, HttpServletRequest request,
                        HttpServletResponse response, RedirectAttributes attributes) {
        session = request.getSession();
        member = userService.login(member);

        if(member == null) {
            session.setAttribute("member", null);
            attributes.addFlashAttribute("msg", false);
        } else {
            session.setAttribute("member", member);
        }
        return "redirect:/";
    }
}
