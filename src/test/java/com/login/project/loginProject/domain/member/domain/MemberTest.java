package com.login.project.loginProject.domain.member.domain;


import com.login.project.loginProject.domain.member.util.GivenMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class MemberTest {

    @Test
    @DisplayName("생년월일을 통해 나이를 알 수 있다.")
    void ageTest() {
        // given, when
        Member member = GivenMember.toEntityNoEncoder();

        int nowYear = LocalDate.now().getYear();

        assertThat(member.getAge()).isEqualTo(nowYear - 1999);
    }
}
