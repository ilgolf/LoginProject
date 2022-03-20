package com.login.project.loginProject.domain.member.util;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class GivenMember {

    public static final String GIVEN_EMAIL = "ilgolc@naver.com";
    public static final String GIVEN_PASSWORD = "123456789";
    public static final String GIVEN_NICKNAME = "ssar";
    public static final String GIVEN_NAME = "kim3";
    public static final LocalDate GIVEN_BIRTH = LocalDate.of(1999, 10, 25);

    public static Member toEntity(final PasswordEncoder encoder) {
        return Member.builder()
                .email(GIVEN_EMAIL)
                .password(encoder.encode(GIVEN_PASSWORD))
                .nickname(GIVEN_NICKNAME)
                .name(GIVEN_NAME)
                .roleType(RoleType.USER)
                .birth(GIVEN_BIRTH)
                .build();
    }

    public static Member toEntityNoEncoder() {
        return Member.builder()
                .email(GIVEN_EMAIL)
                .password(GIVEN_PASSWORD)
                .nickname(GIVEN_NICKNAME)
                .name(GIVEN_NAME)
                .roleType(RoleType.USER)
                .birth(GIVEN_BIRTH)
                .build();
    }
}
