package com.example.board.boardController.controller;

import com.example.board.S3.S3Service;
import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private S3Service s3Service;

    @Mock
    private BoardService boardService;

    @Test
    @DisplayName("새 게시판 생성")
    void createBoard() throws Exception {

        // Given 테스트 실행 준비
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L); // 여러분의 memberId로 대체하세요
        session.setAttribute("KakaoId", 1L); // 여러분의 KakaoId로 대체하세요
        session.setAttribute("email", "hyojin@naver.com"); // 여러분의 이메일로 대체하세요

        MockMultipartFile file = new MockMultipartFile("BoardFile", "test.png", "image/jpeg", "some image".getBytes());


        // When 테스트 진행
        mockMvc.perform(
                        multipart("/createBoardDto")
                                .file(file)
                                .session(session)
                                .flashAttr("createBoardDto", CreateBoardDto.builder()
                                        .title("TestTitle")
                                        .categoryEnum(CategoryEnum.TENNIS)
                                        .contents("TestContents")
                                        .build())
                )

                // Then 테스트 결과 검증
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board"));

    }

    @Test
    @DisplayName("게시판 수정 (이미지 수정 가능)")
    void updateBoard() throws Exception {

        /*
        Given 테스트 실행 준비
         */
        Long boardId = 1L;
        UpdateBoard updateBoard = new UpdateBoard();
        updateBoard.setTitle("TestTitle");
        updateBoard.setCategory(CategoryEnum.TENNIS);
        updateBoard.setContents("TestContents");

        MockMultipartFile file = new MockMultipartFile("BoardFile", "test.png", "image/jpeg", "some image".getBytes());


        /*
        When  테스트 실행
         */
        mockMvc.perform(
                        MockMvcRequestBuilders.multipart("/board/{boardId}/edit", boardId)
                                .file(file)
                                .flashAttr("updateBoard", updateBoard)
                )
                /*
              Then 테스트 결과 검증
                 */
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/board"));

    }
}