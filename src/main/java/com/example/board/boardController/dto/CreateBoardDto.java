package com.example.board.boardController.dto;

import com.example.board.boardController.entity.CategoryEnum;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardDto {
    private Long memberId;
    private Long kakaoId;
    private CategoryEnum categoryEnum;
    private String boardImageUrl; // 게시판 이미지 업로드 로직
    private String title;
    private String contents;
}
