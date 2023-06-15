package com.example.board.boardController.controller;


import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @PostMapping("/createBoard")
    public String createBoard(@Valid @ModelAttribute("createBoardDto") CreateBoardDto createBoardDto,
                              BindingResult bindingResult,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "createBoardDto";
        }

        String nickName = (String) session.getAttribute("nickName");
        createBoardDto.setNickName(nickName);

        boardService.board(createBoardDto.getNickName(),
                createBoardDto.getCategoryEnum(),
                createBoardDto.getTitle(),
                createBoardDto.getContents());

        redirectAttributes.addFlashAttribute("message", "게시글이 작성되었습니다.");

        // 게시글 작성 후 board.jsp로 이동
        return "redirect:/board"; // board.jsp의 경로에 맞게 수정해주세요.
    }

    @PostMapping("/board/{boardId}/edit")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute UpdateBoard updateBoard) {
        boardService.updateBoard(boardId, updateBoard.getCategory(), updateBoard.getTitle(), updateBoard.getContents());
        return "redirect:/board";
    }

    @DeleteMapping("/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/board/";
    }
}
