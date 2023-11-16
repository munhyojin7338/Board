package com.example.board.boardController.controller;

import com.example.board.S3.S3Service;
import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.service.BoardService;
import com.example.board.memberController.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class BoardControllerTest {

    @InjectMocks
    private BoardController boardController;

    @Mock
    private BoardService boardService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private S3Service s3Service;

    @Test
    void createBoard() throws Exception {
        // Given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);

        CreateBoardDto createBoardDto = new CreateBoardDto();
        createBoardDto.setTitle("Test Title");
        createBoardDto.setContents("Test Contents");

        // Mock BoardService
        when(boardService.createBoard(any(), any(), any()))
                .thenReturn(1L);


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();

        // When
        mockMvc.perform(post("/createBoardDto")
                        .session(session)
                        .flashAttr("createBoardDto", createBoardDto))
                .andExpect(status().isOk());
    }

}