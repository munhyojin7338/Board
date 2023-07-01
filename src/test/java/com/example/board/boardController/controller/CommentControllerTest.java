package com.example.board.boardController.controller;

import com.example.board.boardController.dto.CommentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class CommentControllerTest {

    @Autowired
    private CommentController commentController;

    @Test
    void createComment_Success() {
        // Given
        Long boardId = 1L;
        String content = "Test Comment";
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(content);

        // When
        ResponseEntity<String> response = commentController.createComment(boardId, commentDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("댓글이 작성되었습니다.", response.getBody());
    }
    @Test
    void deleteComment_Success() {
        // Given
        Long boardId = 1L;

        // When
        ResponseEntity<?> response = commentController.deleteComment(boardId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

}