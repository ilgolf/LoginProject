package com.login.project.loginProject.domain.member.application;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.exception.MemberNotFoundException;
import com.login.project.loginProject.global.error.exception.ErrorCode;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    // 회원 정보
    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        log.info("{} : 조회 성공!", id);
        Member member = memberRepository.findById(id).orElseGet(Member::new);

        return MemberResponse.from(member);
    }

    // 회원 가입
    public Long signUp(Member requestMember) throws DuplicateMemberException {
        if (memberRepository.findByEmail(requestMember.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입된 정보입니다.");
        }

        String rawPassword = requestMember.getPassword();
        String encodePassword = encoder.encode(rawPassword);

        Member member = Member.builder()
                .email(requestMember.getEmail())
                .password(encodePassword)
                .name(requestMember.getName())
                .nickname(requestMember.getNickname())
                .age(requestMember.getAge())
                .roleType(RoleType.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public void updateMember(Member update, String email) throws DuplicateMemberException {
        if (checkDuplicateEmail(update.getEmail())) {
            throw new DuplicateMemberException("이메일이 이미 존재 합니다.");
        }

        if (checkDuplicateNickName(update.getNickname())) {
            throw new DuplicateMemberException("닉네임이 이미 존재 합니다.");
        }

        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        findMember.update(update);
    }

    private boolean checkDuplicateEmail(String valid) {
        return memberRepository.existsByEmail(valid);
    }

    private boolean checkDuplicateNickName(String valid) {
        return memberRepository.existsByNickname(valid);
    }

    public void delete(final String email) {
        memberRepository.deleteByEmail(email);
    }

    public List<MemberResponse> findAll(Pageable pageable) {
        Page<Member> findMemberAll = memberRepository.findAll(pageable);

        return findMemberAll.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }
}
