package com.login.project.loginProject.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoginDTO {

    @NotNull @Email
    @Size(min = 10, max = 30)
    private String email;

    @NotNull
    @Size(min = 10, max = 30)
    private String password;
}
