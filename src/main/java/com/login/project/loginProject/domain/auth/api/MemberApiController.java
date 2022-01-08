package com.login.project.loginProject.domain.auth.api;

import com.login.project.loginProject.domain.auth.application.MemberService;
import com.login.project.loginProject.domain.member.dto.MemberDTO;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 정보 api
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> lookup(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.lookup(id));
    }

    // 회원 가입 api
    @PostMapping("/join")
    public ResponseEntity<MemberResponse> register(@Valid @RequestBody MemberDTO memberDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 가입 성공", memberDTO.getEmail());
        return ResponseEntity.ok(memberService.signUp(memberDTO));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long memberId,
                                         @Valid @RequestBody MemberUpdateDTO updateDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 수정", updateDTO.getEmail());
        return ResponseEntity.ok(memberService.updateMember(memberId, updateDTO));
    }

    @DeleteMapping("/{memberId}")
    public void delete(@PathVariable @NotNull Long memberId) {
        memberService.delete(memberId);
    }
}
