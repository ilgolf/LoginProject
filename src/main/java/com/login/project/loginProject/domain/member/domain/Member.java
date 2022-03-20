package com.login.project.loginProject.domain.member.domain;

import com.login.project.loginProject.global.common.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private LocalDate birth;

    private int age;

    @Builder
    protected Member(final Long id, final String email, final String password, RoleType roleType,
                  final String nickname, final String name, final LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roleType = roleType;
        this.nickname = nickname;
        this.name = name;
        this.birth = birth;
        this.age = addAge(birth);
    }

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    /**
     * 비즈 니스 로직
     */
    public static Member createMember(final String email, final String password, final String nickname) {
        return new Member(email, password, nickname);
    }

    public void update(final Member member, final PasswordEncoder encoder) {
        changeEmail(member.getEmail());
        changeNickName(member.getNickname());
        changePassword(encoder.encode(member.getPassword()));
    }

    private void changeEmail(final String email) { this.email = email; }

    private void changePassword(final String password) { this.password = password; }

    private void changeNickName(final String nickName) { this.nickname = nickName; }

    private int addAge(final LocalDate birth) {
        int memberYear = birth.getYear();
        int nowYear = LocalDate.now().getYear();

        return nowYear - memberYear;
    }
}
