package com.login.project.loginProject.domain.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 30)
    private String email;

    @Column(unique = true, length = 30)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    @Column(unique = true, length = 20)
    private String nickName;

    @Column(length = 10)
    private String name;

    private int age;

    @Builder
    public Member(String email, String password, RoleType roleType, String nickName, String name, int age) {
        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    public void updateEmail(final String email) {
        this.email = email;
    }

    public void updatePassword(final String password) {
        this.password = password;
    }

    public void updateNickName(final String nickName) {
        this.nickName = nickName;
    }
}
