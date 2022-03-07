package com.login.project.loginProject.domain.member.application;

import com.login.project.loginProject.domain.member.application.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import com.login.project.loginProject.domain.member.exception.MemberNotFoundException;
import com.login.project.loginProject.domain.member.util.GivenMember;
import com.login.project.loginProject.global.error.exception.ErrorCode;
import javassist.bytecode.DuplicateMemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static com.login.project.loginProject.domain.member.util.GivenMember.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder encoder;

    @Test
    @DisplayName("회원 조회 테스트")
    void readMember() {
        // given
        Member givenMember = toEntity(encoder);

        Member member = memberRepository.save(givenMember);

        // when
        MemberResponse response = memberService.findByEmail(member.getEmail());

        Member newMember = memberRepository.findByEmail(response.getEmail()).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        // then
        assertThat(newMember.getEmail()).isEqualTo(GIVEN_EMAIL);
        assertThat(newMember.getAge()).isEqualTo(GIVEN_AGE);
        assertAll(
                () -> assertThat(newMember.getPassword()).isNotEqualTo(GIVEN_PASSWORD),
                () -> assertThat(encoder.matches(GIVEN_PASSWORD, newMember.getPassword())).isTrue()
        );
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMember() throws DuplicateMemberException {
        // given
        Member givenMember = toEntity(encoder);

        Member savedMember = memberRepository.save(givenMember);

        MemberUpdateDTO updateMember = MemberUpdateDTO.builder()
                .email("ssar@naver.com")
                .password("12345")
                .nickname("Dev.Golf")
                .build();

        // when
        memberService.updateMember(updateMember.toEntity(), savedMember.getEmail());

        // then
        assertThat(savedMember.getEmail()).isEqualTo("ssar@naver.com");
        assertThat(encoder.matches("12345", savedMember.getPassword())).isTrue();
        assertThat(savedMember.getNickname()).isEqualTo("Dev.Golf");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteTest() {
        // given
        Member givenMember = toEntity(encoder);

        Member member = memberRepository.save(givenMember);

        String userEmail = member.getEmail();

        // when
        memberService.delete(member.getEmail());

        // then
        assertThrows(IllegalArgumentException.class,
                () -> memberRepository.findByEmail(userEmail).orElseThrow(
                        () -> new IllegalArgumentException("잘못된 접근입니다."))
        );
    }
}