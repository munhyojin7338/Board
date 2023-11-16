package com.example.board.boardController.controller;

import com.example.board.S3.S3Service;
import com.example.board.boardController.dto.CreateBoardDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("새 게시판 생성")
    void createBoard() throws Exception {

        // Given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L); // 여러분의 memberId로 대체하세요
        session.setAttribute("KakaoId", 1L); // 여러분의 KakaoId로 대체하세요
        session.setAttribute("email", "hyojin@naver.com"); // 여러분의 이메일로 대체하세요

        MockMultipartFile file = new MockMultipartFile("BoardFile", "test.png", "image/jpeg", "some image".getBytes());


        // When
        mockMvc.perform(
                        multipart("/createBoardDto")
                                .file(file)
                                .session(session)
                                .flashAttr("createBoardDto", CreateBoardDto.builder()
                                        .title("TestTitle")
                                        .contents("TestContents")
                                        .build())
                )
                // Then
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board"));

    }

}