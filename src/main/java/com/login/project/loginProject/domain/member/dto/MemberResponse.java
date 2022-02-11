package com.login.project.loginProject.domain.member.dto;

import com.login.project.loginProject.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponse {

    private String email;

    private String nickname;

    private String name;

    private int age;

    private MemberResponse(String email, String nickname, String name, int age) {
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.age = age;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getEmail(), member.getNickname(),
                member.getName(), member.getAge());
    }
}
