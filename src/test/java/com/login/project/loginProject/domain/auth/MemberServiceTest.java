package com.login.project.loginProject.domain.auth;

import com.login.project.loginProject.domain.auth.application.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import javassist.bytecode.DuplicateMemberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    private static Member givenMember;

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    Member saveMember() {
        return memberRepository.save(givenMember);
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void readMember() {
        // given
        givenMember = Member.builder()
                .email("ilgolc@naver.com")
                .password("1234")
                .nickName("Golf")
                .name("kim")
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member member = saveMember();

        // when
        MemberResponse response = memberService.findById(member.getId());

        Member newMember = memberRepository.findByEmail(response.getEmail()).orElseGet(Member::new);

        // then
        assertThat(newMember.getEmail()).isEqualTo("ilgolc@naver.com");
        assertThat(newMember.getAge()).isEqualTo(26);
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMember() throws DuplicateMemberException {
        // given
        givenMember = Member.builder()
                .email("ilgolc@naver.com")
                .password("1234")
                .nickName("Golf")
                .name("kim")
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member savedMember = saveMember();

        MemberUpdateDTO updateMember = MemberUpdateDTO.builder()
                .email("ssar@naver.com")
                .password("12345")
                .nickName("Dev.Golf")
                .build();

        // when
        Long updateId = memberService.updateMember(updateMember.toEntity(), savedMember.getId());

        Member member = memberRepository.findById(updateId).orElseGet(Member::new);

        // then
        assertThat(member.getEmail()).isEqualTo("ssar@naver.com");
        assertThat(member.getPassword()).isEqualTo("12345");
        assertThat(member.getNickName()).isEqualTo("Dev.Golf");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteTest() {
        // given
        givenMember = Member.builder()
                .email("ilgolc@naver.com")
                .password("1234")
                .nickName("Golf")
                .name("kim")
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member member = saveMember();

        String userEmail = member.getEmail();

        // when
        memberService.delete(member.getId());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
                    memberRepository.findByEmail(userEmail).orElseThrow(
                            () -> new IllegalArgumentException("잘못된 접근입니다.")
                    );
                }
        );
    }
}