package com.example.board.boardController.controller;


import com.example.board.S3.S3Service;
import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    private final S3Service s3Service;

    // 게시판 로직 생성
    @PostMapping("/createBoard")
    public String createBoard(@Valid @ModelAttribute("createBoardDto") CreateBoardDto createBoardDto,
                              RedirectAttributes redirectAttributes) {

        boardService.board(createBoardDto.getId(),
                createBoardDto.getCategoryEnum(),
                createBoardDto.getTitle(),
                createBoardDto.getContents());

        redirectAttributes.addFlashAttribute("message", "게시글이 작성되었습니다.");

        return "redirect:/board";
    }


    // 게시글 수정
    @PostMapping("/board/{boardId}/edit")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute UpdateBoard updateBoard) {
        boardService.updateBoard(boardId, updateBoard.getCategory(), updateBoard.getTitle(), updateBoard.getContents());
        return "redirect:/board";
    }

    // 게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/";
    }

    // 게시글 이미지 업로드 하기
    @PostMapping("/board/upload/{boardId}")
    public ResponseEntity<?> uploadBoardFile(@RequestParam("BoardFile") MultipartFile file
            , @PathVariable Long boardId) throws IOException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글 정보가 없습니다."));

        String boardFileName = s3Service.upload(file);

        board.setBoardImageUrl(boardFileName);

        boardRepository.save(board);

        return ResponseEntity.ok().build();
    }


}
