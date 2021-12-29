package com.login.project.loginProject.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @NotNull @Email
    @Size(min = 5, max = 30)
    private String email;

    @NotNull
    @Size(min = 8, max = 30)
    private String password;

    @NotNull
    @Size(min = 3, max = 10)
    private String name;

    @NotNull
    @Size(min = 4, max = 20)
    private String nickName;

    @NotNull
    private int age;
}
