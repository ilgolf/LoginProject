package com.login.project.loginProject.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    @Size(min = 5, max = 30)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 3, max = 10)
    private String name;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(min = 4, max = 20)
    private String nickName;

    @NotBlank(message = "나이를 입력해주세요")
    private int age;
}
