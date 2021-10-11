package com.login.project.loginProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO<T> {

    int status;
    T data;
}
