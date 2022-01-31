package com.login.project.loginProject.domain.auth.principal;

import com.login.project.loginProject.domain.auth.exception.EmailNotFoundException;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new EmailNotFoundException(ErrorCode.EMAIL_DUPLICATION)
        );

        return new PrincipalDetail(member);
    }
}
