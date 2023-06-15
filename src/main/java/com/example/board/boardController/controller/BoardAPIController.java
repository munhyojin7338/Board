package com.example.board.boardController.controller;

import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardAPIController {

    private final BoardService boardService;


   // 게시판 메인 페이지
   @GetMapping("/board")
   public String board(Model model) {
       List<Board> boardList = boardService.getAllBoards();
       model.addAttribute("boardList", boardList);
       return "board";
   }
    // 게시판 생성 페이지
    @GetMapping("/createBoardDto")
    public String createBoardForm() {
        return "createBoardDto";
    }

    // 게시판 수정 페이지
    @GetMapping("/board/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) {
        Board board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        return "boardDetail";
    }

    // 게시판 수정하기
    @GetMapping("/board/{boardId}/edit")
    public String editBoard(@PathVariable Long boardId, Model model) {
        Board board = boardService.getBoardById(boardId);
        UpdateBoard updateBoard = new UpdateBoard(board.getId(), board.getCategory(), board.getTitle(), board.getContents());
        model.addAttribute("updateBoard", updateBoard);
        model.addAttribute("categories", CategoryEnum.values()); // 카테고리 목록을 모델에 추가
        return "editBoard";
    }






}
