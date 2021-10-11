package com.login.project.loginProject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String username;
    private String password;
}
