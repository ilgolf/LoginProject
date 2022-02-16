package com.login.project.loginProject.domain.member.domain;

import com.login.project.loginProject.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, length = 30)
    private String email;

    @Column(unique = true, length = 120)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type", length = 15)
    private RoleType roleType;

    @Column(unique = true, length = 20)
    private String nickname;

    @Column(length = 20)
    private String name;

    private Integer age;

    @Builder
    public Member(String email, String password, RoleType roleType, String nickname, String name, Integer age) {

        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
    }

    /**
     * 비즈 니스 로직
     */
    public void update(final Member member) {
        changeEmail(member.getEmail());
        changePassword(member.getPassword());
        changeNickName(member.getNickname());
    }

    private void changeEmail(final String email) { this.email = email; }

    private void changePassword(final String password) { this.password = password; }

    private void changeNickName(final String nickName) { this.nickname = nickName; }
}
