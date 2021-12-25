package com.login.project.loginProject.domain.auth.api;

import com.login.project.loginProject.domain.auth.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.dto.MemberDTO;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 가입 api
    @PostMapping("/join")
    public ResponseEntity<Member> register(@Valid @RequestBody MemberDTO memberDTO) throws DuplicateMemberException {
        return ResponseEntity.ok(memberService.signUp(memberDTO));
    }


}
