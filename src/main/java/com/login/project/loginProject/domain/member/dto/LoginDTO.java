package com.login.project.loginProject.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoginDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 아닙니다.")
    @Size(min = 10, max = 30)
    private String email;

    @NotBlank(message = "이메일을 입력해주세요")
    @Size(min = 10, max = 30)
    private String password;
}
