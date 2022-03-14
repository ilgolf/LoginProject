package com.login.project.loginProject.domain.member.domain;

import com.login.project.loginProject.global.common.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, length = 30)
    private String email;

    @Column(length = 120)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type", length = 15)
    private RoleType roleType;

    @Column(unique = true, length = 20)
    private String nickname;

    @Column(length = 20)
    private String name;

    private int age = 0;

    /**
     * 비즈 니스 로직
     */
    public void update(final Member member, final PasswordEncoder encoder) {
        changeEmail(member.getEmail());
        changeNickName(member.getNickname());
        changePassword(encoder.encode(member.getPassword()));
    }

    private void changeEmail(final String email) { this.email = email; }

    private void changePassword(final String password) { this.password = password; }

    private void changeNickName(final String nickName) { this.nickname = nickName; }
}
