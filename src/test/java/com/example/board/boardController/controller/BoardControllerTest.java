package com.example.board.boardController.controller;

import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.entity.CategoryEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;



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

    @Test
    @DisplayName("게시판 삭제 - 실패: 게시물을 찾을 수 없음")
    void deleteBoardFailed() throws Exception {
        // Given 테스트의 초기 조건 / 실행 준비
        Long nonExistentBoardId = 999L; // 존재하지 않는 게시물 ID

        // When 테스트 실행: 지정된 URI("/board/{boardId}")에 대한 HTTP DELETE 요청 수행
        mockMvc.perform(delete("/board/{boardId}", nonExistentBoardId))

                // Then 테스트 결과 검증
                .andExpect(status().is3xxRedirection()) // 3xx 리다이렉션 상태 확인
                .andExpect(redirectedUrl("/board")) // 리다이렉트된 URL이 "/board"인지 확인
                .andExpect(result -> {
                    String responseBody = mockMvc.perform(get("/board"))
                            .andReturn().getResponse().getContentAsString();

                    System.out.println("Response Body: " + responseBody);

                    mockMvc.perform(get("/board")
                                    .content(responseBody))
                            .andExpect(content().string(containsString("게시물을 찾을 수 없습니다.")));
                });
    }



    @Test
    @DisplayName("게시판 삭제 - 성공")
    void deleteBoardSuccess() throws Exception {
        // Given 테스트의 초기 조건 / 실행 준비
        Long existingBoardId = 1L; // 존재하는 게시물 ID

        // When 테스트 실행: 지정된 URI("/board/{boardId}")에 대한 HTTP DELETE 요청 수행
        mockMvc.perform(delete("/board/{boardId}", existingBoardId))

                // Then 테스트 결과 검증
                .andExpect(status().isOk()) // 예상되는 응답 상태 코드는 200 (OK)
                .andExpect(redirectedUrl("/board")); // 삭제 후 리다이렉트되는 URL 확인
    }


}