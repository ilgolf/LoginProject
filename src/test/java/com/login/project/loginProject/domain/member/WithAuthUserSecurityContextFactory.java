package com.login.project.loginProject.domain.member;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        Member member = Member.builder()
                .id(annotation.id())
                .email(annotation.email())
                .password(annotation.password())
                .name(annotation.name())
                .roleType(RoleType.USER)
                .nickname(annotation.nickname())
                .age(annotation.age())
                .build();

        List<GrantedAuthority> role =
                AuthorityUtils.createAuthorityList(RoleType.USER.name(), RoleType.Stranger.name());

        SecurityContextHolder.getContext().setAuthentication
                (new UsernamePasswordAuthenticationToken(member.getEmail(), annotation.password(), role));

        return SecurityContextHolder.getContext();
    }
}
