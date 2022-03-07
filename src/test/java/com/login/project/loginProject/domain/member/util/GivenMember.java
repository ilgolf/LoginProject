package com.login.project.loginProject.domain.member.util;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GivenMember {

    public static final String GIVEN_EMAIL = "ilgolc@naver.com";
    public static final String GIVEN_PASSWORD = "123456789";
    public static final String GIVEN_NICKNAME = "ssar";
    public static final String GIVEN_NAME = "kim3";
    public static final Integer GIVEN_AGE = 23;

    public static Member toEntity(final PasswordEncoder encoder) {
        return Member.builder()
                .email(GIVEN_EMAIL)
                .password(encoder.encode(GIVEN_PASSWORD))
                .nickname(GIVEN_NICKNAME)
                .name(GIVEN_NAME)
                .roleType(RoleType.USER)
                .age(GIVEN_AGE)
                .build();
    }
}
