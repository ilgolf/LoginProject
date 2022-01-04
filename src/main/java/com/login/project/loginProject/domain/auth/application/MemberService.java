package com.login.project.loginProject.domain.auth.application;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberDTO;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
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
    public MemberResponse lookup(Long id) {
        log.info("{} : 조회 성공!", id);
        Member member = memberRepository.findById(id).orElseGet(Member::new);

        return new MemberResponse(member);
    }

    // 회원 가입
    public MemberResponse signUp(MemberDTO memberDTO) throws DuplicateMemberException {
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

        return new MemberResponse(member);
    }

    public MemberResponse updateMember(Long memberId, MemberUpdateDTO updateDTO) throws DuplicateMemberException {
        if (checkDuplicateEmail(updateDTO.getEmail())) {
            throw new DuplicateMemberException("이메일이 이미 존재 합니다.");
        }

        if (checkDuplicateNickName(updateDTO.getNickName())) {
            throw new DuplicateMemberException("닉네임이 이미 존재 합니다.");
        }

        Member member = memberRepository.findById(memberId).orElseGet(Member::new);

        member.update(updateDTO.getEmail(), updateDTO.getPassword(), updateDTO.getNickName());

        return new MemberResponse(member);
    }

    private boolean checkDuplicateEmail(String valid) {
        return memberRepository.existsByEmail(valid);
    }

    private boolean checkDuplicateNickName(String valid) {
        return memberRepository.existsByNickName(valid);
    }
}
