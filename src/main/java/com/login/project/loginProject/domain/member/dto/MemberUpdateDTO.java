package com.login.project.loginProject.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberUpdateDTO {

    @Email @NotNull
    @Size(min = 10, max = 30)
    private String email;

    @NotNull
    @Size(min = 10, max = 30)
    private String password;

    @NotNull
    @Size(min = 4, max = 20)
    private String nickName;
}
