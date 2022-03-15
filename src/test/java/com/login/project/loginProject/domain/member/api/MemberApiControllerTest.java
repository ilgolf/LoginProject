package com.login.project.loginProject.domain.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.project.loginProject.domain.member.WithAuthUser;
import com.login.project.loginProject.domain.member.application.MemberService;
import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.dto.JoinRequest;
import com.login.project.loginProject.domain.member.dto.MemberResponse;
import com.login.project.loginProject.domain.member.dto.MemberUpdateDTO;
import com.login.project.loginProject.domain.member.util.GivenMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.login.project.loginProject.domain.member.util.GivenMember.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원 생성 컨트롤러")
    void create() throws Exception {

        JoinRequest memberDTO = JoinRequest.builder()
                .email("ssar@naver.com")
                .password("123456789")
                .name("ssar")
                .nickname("ssss")
                .age(23)
                .build();

        String body = objectMapper.writeValueAsString(memberDTO);

        when(memberService.signUp(any())).thenReturn("ssar@naver.com");

        mockMvc.perform(post("/members").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("member/created",
                        requestFields(
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호"),
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("nickname").description("회원 별칭"),
                                fieldWithPath("age").description("회원 나이")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 이메일로 조회 컨트롤러")
    @WithAuthUser
    void findByEmail() throws Exception {
        when(memberService.findByEmail(any())).thenReturn(MemberResponse.from(toEntityNoEncoder()));

        mockMvc.perform(get("/members/email")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member/findOne",
                        responseFields(
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("name").description("회원 이름"),
                                fieldWithPath("nickname").description("회원 별칭"),
                                fieldWithPath("age").description("회원 나이")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 수정 컨트롤러")
    @WithAuthUser
    void update() throws Exception {
        MemberUpdateDTO updateDTO = MemberUpdateDTO.builder()
                .email("ilgolc@naver.com")
                .password("abcdefghijk")
                .nickname("kim23")
                .build();

        String body = objectMapper.writeValueAsString(updateDTO);

        mockMvc.perform(patch("/members").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member/update",
                        requestFields(
                                fieldWithPath("email").description("회원 이메일"),
                                fieldWithPath("password").description("회원 비밀번호"),
                                fieldWithPath("nickname").description("회원 별칭")
                        )))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 삭제 컨트롤러")
    @WithAuthUser
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/members"))
                .andExpect(status().isNoContent())
                .andDo(document("member/delete"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 전체 조회 테스트")
    @WithAuthUser
    void findAllTest() throws Exception {
        List<MemberResponse> members = List.of(MemberResponse.from(toEntityNoEncoder()));

        when(memberService.findAll(any())).thenReturn(members);

        mockMvc.perform(get("/members")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("/member/memberAll"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 검색 컨트롤러 테스트")
    @WithAuthUser
    void searchTest() throws Exception {
        List<MemberResponse> members = List.of(MemberResponse.from(toEntityNoEncoder()));

        when(memberService.searchMember(any(), any())).thenReturn(members);

        mockMvc.perform(get("/members/{name}", GIVEN_NAME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("member/searchMember"))
                .andDo(print());
    }
}