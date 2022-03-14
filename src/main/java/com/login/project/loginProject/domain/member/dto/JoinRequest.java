package com.login.project.loginProject.domain.member.dto;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NotBlank(message = "필수 값입니다.")
public class JoinRequest {

    @Email
    @Size(min = 5, max = 30)
    private String email;

    @Size(min = 8, max = 30)
    private String password;

    @Size(min = 3, max = 10)
    private String name;

    @Size(min = 4, max = 20)
    private String nickname;

    private int age;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .roleType(RoleType.USER)
                .nickname(nickname)
                .age(age)
                .build();
    }
}
