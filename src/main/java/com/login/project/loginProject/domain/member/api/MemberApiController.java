package com.login.project.loginProject.domain.member.api;

import com.login.project.loginProject.domain.member.application.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberDTO;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 정보 api
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    // 회원 가입 api
    @PostMapping("/join")
    public ResponseEntity<Long> register(@Valid @RequestBody MemberDTO memberDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 가입 성공", memberDTO.getEmail());
        Member member = memberDTO.toEntity();
        return new ResponseEntity<>(memberService.signUp(member), HttpStatus.CREATED);
    }

    // 회원 변경
    @PutMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal String email, @Valid @RequestBody MemberUpdateDTO updateDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 수정", updateDTO.getEmail());
        Member member = updateDTO.toEntity();
        memberService.updateMember(member, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal String email) {
        memberService.delete(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(
            @PageableDefault(size = 5, sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(memberService.findAll(pageable));
    }

    /**
     * 페이징 테스트용 로직
     */
    @PostConstruct
    void initializing() throws DuplicateMemberException {
        for (int i = 0; i < 50; i++) {
            Member member = Member.builder()
                    .email("member" + (i + 1) + "@naver.com")
                    .password("123" + i)
                    .name("kim" + i)
                    .nickname("fasdf" + (i + 1))
                    .age(10 + i)
                    .roleType(RoleType.USER)
                    .build();

            memberService.signUp(member);
        }
    }
}
