package com.login.project.loginProject.domain.auth.principal;

import com.login.project.loginProject.domain.auth.exception.EmailNotFoundException;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws EmailNotFoundException {
        return memberRepository.findByEmail(email)
                .map(CustomUserDetails::createDetails)
                .orElseThrow(() -> new EmailNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
