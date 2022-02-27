package com.login.project.loginProject.domain.auth;

import com.login.project.loginProject.domain.member.application.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import javassist.bytecode.DuplicateMemberException;
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
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 조회 테스트")
    void readMember() {
        // given
        Member givenMember = Member.builder()
                .email("ilgolc@naver.com")
                .password("1234")
                .nickname("Golf")
                .name("kim")
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member member = memberRepository.save(givenMember);

        // when
        MemberResponse response = memberService.findByEmail(member.getEmail());

        Member newMember = memberRepository.findByEmail(response.getEmail()).orElseGet(Member::new);

        // then
        assertThat(newMember.getEmail()).isEqualTo("ilgolc@naver.com");
        assertThat(newMember.getAge()).isEqualTo(26);
    }

    @Test
    @DisplayName("회원 수정 테스트")
    void updateMember() throws DuplicateMemberException {
        // given
        Member givenMember = Member.builder()
                .email("ilgolc@naver.com")
                .password("1234")
                .nickname("Golf")
                .name("kim")
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member savedMember = memberRepository.save(givenMember);

        MemberUpdateDTO updateMember = MemberUpdateDTO.builder()
                .email("ssar@naver.com")
                .password("12345")
                .nickName("Dev.Golf")
                .build();

        // when
        memberService.updateMember(updateMember.toEntity(), savedMember.getEmail());

        // then
        assertThat(savedMember.getEmail()).isEqualTo("ssar@naver.com");
        assertThat(savedMember.getPassword()).isEqualTo("12345");
        assertThat(savedMember.getNickname()).isEqualTo("Dev.Golf");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void deleteTest() {
        // given
        Member givenMember = Member.builder()
                .email("ilgolc@naver.com")
                .password("1234")
                .nickname("Golf")
                .name("kim")
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member member = memberRepository.save(givenMember);

        String userEmail = member.getEmail();

        // when
        memberService.delete(member.getEmail());

        // then
        assertThrows(IllegalArgumentException.class, () -> {
                    memberRepository.findByEmail(userEmail).orElseThrow(
                            () -> new IllegalArgumentException("잘못된 접근입니다.")
                    );
                }
        );
    }
}