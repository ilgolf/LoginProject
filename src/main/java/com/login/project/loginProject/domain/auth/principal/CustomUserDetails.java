package com.login.project.loginProject.domain.auth.principal;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Slf4j
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String name;
    private final Integer age;

    private CustomUserDetails(Long id, String email, String nickname, String name, Integer age) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + RoleType.USER));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        log.debug("CustomDetails.Email : {} ", email);
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static CustomUserDetails from(final Member member) {
        return new CustomUserDetails(member.getId(), member.getEmail(), member.getNickname(),
                member.getName(), member.getAge());
    }
}
