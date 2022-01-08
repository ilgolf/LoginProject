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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = 30)
    private String email;

    @Column(unique = true, length = 30)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_type", length = 15)
    private RoleType roleType;

    @Column(name = "nick_name",unique = true, length = 20)
    private String nickName;

    @Column(length = 20)
    private String name;

    private Integer age;

    @Builder
    public Member(String email, String password, RoleType roleType, String nickName, String name, Integer age) {

        // 검증 로직
        Assert.hasText(email, "이메일이 빠졌습니다.");
        Assert.hasText(password, "비밀번호가 빠졌습니다.");
        Assert.hasText(roleType.name(), "권한이 빠졌습니다.");
        Assert.hasText(nickName, "닉네임이 빠졌습니다.");
        Assert.hasText(name, "이름이 빠졌습니다.");

        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    /**
     * 비즈 니스 로직
     */
    public void update(final String email, final String password, final String nickName) {
        changeEmail(email);
        changePassword(password);
        changeNickName(nickName);
    }

    private void changeEmail(final String email) { this.email = email; }

    private void changePassword(final String password) { this.password = password; }

    private void changeNickName(final String nickName) { this.nickName = nickName; }
}
