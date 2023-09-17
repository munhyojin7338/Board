package com.example.board.boardController.controller;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.service.BoardService;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardAPIController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;


    // 게시판 메인 페이지
    @GetMapping("/board")
    public String board(Model model) {
        List<Board> boardList = boardService.getAllBoards();
        model.addAttribute("boardList", boardList);
        return "board";
    }

    // 게시판 생성 페이지
    @GetMapping("/createBoardDto")
    public String createBoardForm(HttpSession session, Model model, Member member) {
        Long memberId = (Long) session.getAttribute("memberId"); // 세션에서 회원 ID 가져오기
        model.addAttribute("createBoardDto", new CreateBoardDto());
        model.addAttribute("memberId", memberId);
        return "createBoardDto";
    }

    // 게시판 수정 페이지
    // 게시글 조회수 증가
    @GetMapping("/board/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model, HttpSession session) {
        Board board = boardService.getBoardById(boardId);

        if (board != null) {
            Boolean isViewed = (Boolean) session.getAttribute("isViewed_" + boardId);

            if (isViewed == null || !isViewed) {
                board.incrementViews();
                boardService.saveBoard(board);
                session.setAttribute("isViewed_" + boardId, true);
            }

            model.addAttribute("board", board);
            return "boardDetail";
        } else {
            throw new NotFoundException("게시글을 찾을 수 없습니다.");
        }
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