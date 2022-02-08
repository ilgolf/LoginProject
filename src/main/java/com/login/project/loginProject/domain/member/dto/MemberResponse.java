package com.login.project.loginProject.domain.member.dto;

import com.login.project.loginProject.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {

    private String email;

    private String nickName;

    private String name;

    private int age;

    public MemberResponse(final Member member) {
        this.email = member.getEmail();
        this.nickName = member.getNickname();
        this.name = member.getName();
        this.age = member.getAge();
    }
}
