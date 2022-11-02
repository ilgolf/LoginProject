package com.login.project.loginProject.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.RoleType;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {

    @NotBlank(message = "필수 값입니다.")
    @Email
    @Size(min = 5, max = 30)
    private String email;

    @NotBlank(message = "필수 값입니다.")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "필수 값입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]*$")
    @Size(min = 3, max = 10)
    private String name;

    @NotBlank(message = "필수 값입니다.")
    @Size(min = 4, max = 20)
    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .roleType(RoleType.USER)
                .nickname(nickname)
                .birth(birth)
                .build();
    }
}
