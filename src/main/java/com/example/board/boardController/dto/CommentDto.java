package com.example.board.boardController.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private int index;          // 댓글 순서
    private String memberId;      // 작성자
    private String content;     // 댓글 내용
    private LocalDateTime createdAt;  // 작성 시간

}
