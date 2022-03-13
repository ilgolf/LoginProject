package com.login.project.loginProject.domain.member.api;

import com.login.project.loginProject.domain.auth.principal.CustomUserDetails;
import com.login.project.loginProject.domain.member.application.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.JoinRequest;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 회원 정보 api
    @GetMapping("/findByEmail")
    public ResponseEntity<MemberResponse> findMember() {
        String email = getEmail();
        return ResponseEntity.ok(memberService.findByEmail(getEmail()));
    }

    // 회원 가입 api
    @PostMapping("/join")
    public ResponseEntity<String> register(@Valid @RequestBody JoinRequest memberDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 가입 성공", memberDTO.getEmail());
        Member member = memberDTO.toEntity();
        URI uri = URI.create("/members/findByEmail");
        return ResponseEntity.created(uri).body(memberService.signUp(member));
    }

    // 회원 변경
    @PatchMapping
    public ResponseEntity<Void> update(@Valid @RequestBody MemberUpdateDTO updateDTO) throws DuplicateMemberException {
        log.debug("{} : 회원 수정", updateDTO.getEmail());
        memberService.updateMember(updateDTO.toEntity(), getEmail());
        return ResponseEntity.ok().build();
    }

    // 회원 삭제
    @DeleteMapping
    public ResponseEntity<Void> delete() {
        memberService.delete(getEmail());
        return ResponseEntity.noContent().build();
    }

    // 회원 전체 조회
    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(
            @PageableDefault(size = 25, sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(memberService.findAll(pageable));
    }

    private String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
