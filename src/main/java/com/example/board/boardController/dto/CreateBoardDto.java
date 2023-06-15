package com.example.board.boardController.dto;

import com.example.board.boardController.entity.CategoryEnum;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {
    private Long id;
    private CategoryEnum categoryEnum;
    private String title;
    private String contents;
}
