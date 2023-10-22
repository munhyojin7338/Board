package com.example.board.boardController.dto;

import com.example.board.boardController.entity.CategoryEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoard {
    private Long boardId;
    private CategoryEnum category;
    private String title;
    private String contents;
    private String UpBoardImageUrl;
}
