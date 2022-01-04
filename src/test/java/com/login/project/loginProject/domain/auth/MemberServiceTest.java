package com.login.project.loginProject.domain.auth;

import com.login.project.loginProject.domain.auth.service.MemberService;
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

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    private static Member givenMember;

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    Member givenInfo() {
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
                .roleType(RoleType.USER)
                .age(26)
                .build();

        Member member = givenInfo();

        // when
        MemberResponse response = memberService.lookup(member.getId());

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
                .roleType(RoleType.USER)
                .age(26)
                .build();
        Member savedMember = givenInfo();

        MemberUpdateDTO updateDTO = new MemberUpdateDTO();

        updateDTO.setEmail("ssar@naver.com");
        updateDTO.setPassword("12345");
        updateDTO.setNickName("Dev.Golf");

        // when
        MemberResponse memberResponse = memberService.updateMember(savedMember.getId(), updateDTO);

        Member member = memberRepository.findByEmail(memberResponse.getEmail()).orElseGet(Member::new);

        // then
        assertThat(member.getEmail()).isEqualTo("ssar@naver.com");
        assertThat(member.getPassword()).isEqualTo("12345");
        assertThat(member.getNickName()).isEqualTo("Dev.Golf");
    }
}