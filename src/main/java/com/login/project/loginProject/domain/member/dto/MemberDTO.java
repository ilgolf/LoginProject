package com.login.project.loginProject.domain.member.dto;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    @Size(min = 5, max = 30)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(min = 3, max = 10)
    private String name;

    @NotBlank(message = "별명을 입력해주세요")
    @Size(min = 4, max = 20)
    private String nickName;

    @NotNull(message = "나이를 입력해주세요")
    private Integer age;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .roleType(RoleType.USER)
                .nickname(nickName)
                .age(age)
                .build();
    }
}
