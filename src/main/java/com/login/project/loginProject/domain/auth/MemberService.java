package com.login.project.loginProject.domain.auth;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberDTO;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 정보
    @Transactional(readOnly = true)
    public Member lookup(Long id) {
        log.info("{} : 조회 성공!", id);
        return memberRepository.findById(id).orElseGet(Member::new);
    }

    // 회원 가입
    public Member signUp(MemberDTO memberDTO) throws DuplicateMemberException {
        if (memberRepository.findByEmail(memberDTO.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입된 정보입니다.");
        }

        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .nickName(memberDTO.getNickName())
                .age(memberDTO.getAge())
                .roleType(RoleType.USER)
                .build();

        memberRepository.save(member);

        return member;
    }

    public Member updateMember(Long memberId, MemberUpdateDTO updateDTO) throws DuplicateMemberException {
        if (memberRepository.findByEmail(updateDTO.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입된 정보입니다.");
        }

        Member member = memberRepository.findOne(memberId);

        member.updateEmail(updateDTO.getEmail());
        member.updatePassword(updateDTO.getPassword());
        member.updateNickName(updateDTO.getNickName());

        return member;
    }
}
