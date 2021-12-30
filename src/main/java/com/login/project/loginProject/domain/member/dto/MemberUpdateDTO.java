package com.login.project.loginProject.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberUpdateDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 아닙니다.")
    @Size(min = 10, max = 30)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 10, max = 30)
    private String password;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(min = 4, max = 20)
    private String nickName;
}
