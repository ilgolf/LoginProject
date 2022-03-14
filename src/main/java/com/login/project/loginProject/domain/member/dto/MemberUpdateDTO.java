package com.login.project.loginProject.domain.member.dto;

import com.login.project.loginProject.domain.member.domain.Member;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NotBlank(message = "필수 값입니다.")
public class MemberUpdateDTO {

    @Email(message = "이메일 형식이 아닙니다.")
    @Size(min = 10, max = 30)
    private String email;

    @Size(min = 8, max = 30)
    private String password;

    @Size(min = 4, max = 20)
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
