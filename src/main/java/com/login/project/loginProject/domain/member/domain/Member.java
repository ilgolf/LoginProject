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

    @Column(length = 10)
    private String name;

    private int age;

    @Builder
    public Member(String email, String password, RoleType roleType, String name, int age) {
        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.name = name;
        this.age = age;
    }
}
