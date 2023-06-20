package com.example.board.memberController.controller;

import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("로그인 테스트 진행 - 가입되지 않은 e-mail 로그인 할 경우 로그인 실패 ")
    void loginWithNonexistentEmail() throws Exception {
        // given -> 테스트를 수행하기 위해  초기 설정
        String email = "hyojin@naver.com";
        String password = "1234";


        // when -> 테스트하려는 동작 또는 메서드 호출
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", "121212@example.com") // 가입되지 않은 email 삽입
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"));
        // then -> 섹션을 테스트 결과를 검증하는 부분

    }

    @Test
    @DisplayName("올바른 E-MAIL, 틀린 비밀번호로 로그인할 경우 실패")
    void loginWithIncorrectPassword() throws Exception {
        // given
        String email = "test2@naver.com";
        String password = "1234";

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", email)
                        .param("password", "wrong_password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andDo(MockMvcResultHandlers.print());
    }

}