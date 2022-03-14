package com.login.project.loginProject.domain.member.dto;

import com.login.project.loginProject.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NotBlank(message = "필수 값입니다.")
public class MemberResponse {

    @Email
    @Size(min = 5, max = 30)
    private String email;

    @Size(min = 3, max = 10)
    private String nickname;

    @Size(min = 4, max = 20)
    private String name;

    private int age;

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getEmail(), member.getNickname(),
                member.getName(), member.getAge());
    }
}
