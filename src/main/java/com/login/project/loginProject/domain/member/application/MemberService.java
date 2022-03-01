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
    public MemberResponse findByEmail(final String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND)
        );

        return MemberResponse.from(member);
    }

    // 회원 가입
    public Long signUp(final Member requestMember) throws DuplicateMemberException {
        isDuplicate(requestMember);

        Member member = Member.builder()
                .email(requestMember.getEmail())
                .name(requestMember.getName())
                .password(encoder.encode(requestMember.getPassword()))
                .nickname(requestMember.getNickname())
                .age(requestMember.getAge())
                .roleType(RoleType.USER)
                .build()
                ;

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    public void updateMember(final Member update, final String email) throws DuplicateMemberException {
        isDuplicate(update);

        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        });

        findMember.update(update, encoder);
    }

    public void delete(final String email) {
        memberRepository.deleteByEmail(email);
    }

    public List<MemberResponse> findAll(Pageable pageable) {
        List<Member> findMemberAll = memberRepository.findAll(pageable).getContent();

        return findMemberAll.stream()
                .map(MemberResponse::from)
                .collect(Collectors.toList());
    }

    private void isDuplicate(final Member member) throws DuplicateMemberException {
        if (memberRepository.existsByEmail(member.getEmail()) ||
                memberRepository.existsByNickname(member.getNickname())) {
            throw new DuplicateMemberException("이미 존재하는 닉네임 혹은 이메일입니다.");
        }
    }
}
