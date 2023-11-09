package com.example.board.boardController.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
 lombok
 ,@Setter ,@Getter
 ,@AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다.
 ,@NoArgsConstructor :  파라미터가 없는 기본 생성자를 생성
   */

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
