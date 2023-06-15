package com.example.board.boardController.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {
    private Long boardId;

    private String content;
}
