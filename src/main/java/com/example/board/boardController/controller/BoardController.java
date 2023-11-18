package com.example.board.boardController.controller;


import com.example.board.S3.S3Service;
import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.dto.UpdateBoard;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.service.BoardService;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

/*
 JPA
 ,REST API(POST, GET, DELETE)
 ,AWS S3
 */
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    private final S3Service s3Service;
    private final MemberRepository memberRepository;

    // 게시판 로직 생성
    @PostMapping("/createBoardDto")
    public String createBoard(@Valid @ModelAttribute("createBoardDto") CreateBoardDto createBoardDto
            , HttpSession session
            , @RequestParam("BoardFile") MultipartFile image) {

        System.out.println("1");
        Long memberId = (Long) session.getAttribute("memberId");
        System.out.println("memberId 는 " + memberId);

        System.out.println("2");
        Long KakaoId = (Long) session.getAttribute("KakaoId");
        System.out.println("KakaoId 는 " + KakaoId);

        System.out.println("3");
        String username = (String) session.getAttribute("email");
        System.out.println("Email 는 " + username);

        // 이미지 파일이 업로드되었는지 확인
        if (!image.isEmpty()) { // 이미지 파일이 비어있지 않다면 처리
            try {
                // 이미지 업로드 및 S3에 저장
                String uniqueFileName = s3Service.upload(image); // 이미지 파일 이름을 문자열로 저장
                createBoardDto.setBoardImageUrl(uniqueFileName); // 이미지 파일 이름을 DTO에 저장
            } catch (IOException e) {
                e.printStackTrace(); // 이미지 업로드 실패 시 처리
            }
        }


        if (KakaoId != null) {
            Optional<Member> memberOptional = memberRepository.findByKakaoId(KakaoId);
            if (memberOptional.isPresent()) {
                System.out.println("KakaoID 넣는곳");
                boardService.createBoard(createBoardDto, memberOptional, null);
            } else {
                System.out.println("가입된 회원이 없습니다");
            }
        } else if (memberId != null) {
            Optional<Member> memberIdOptional = memberRepository.findById(memberId);
            if (memberIdOptional.isPresent()) {
                System.out.println("memberId 넣기");
                boardService.createBoard(createBoardDto, memberIdOptional, null);
            } else {
                System.out.println("가입된 회원이 없습니다");
            }
        } else {
            System.out.println("KakaoId와 memberId가 모두 null입니다");
        }

        return "redirect:/board";
    }

    // 게시글 수정
    @PostMapping("/board/{boardId}/edit")
    public String updateBoard(@PathVariable Long boardId
            , @ModelAttribute UpdateBoard updateBoard
            , @RequestParam("BoardFile") MultipartFile image) {

        // 이미지 파일이 업로드되었는지 확인
        if (!image.isEmpty()) { // 이미지 파일이 비어있지 않다면 처리
            try {
                // 이미지 업로드 및 S3에 저장
                String uniqueFileName = s3Service.upload(image); // 이미지 파일 이름을 문자열로 저장
                updateBoard.setUpBoardImageUrl(uniqueFileName); // 이미지 파일 이름을 DTO에 저장
            } catch (IOException e) {
                e.printStackTrace(); // 이미지 업로드 실패 시 처리
            }
        }

        boardService.updateBoard(boardId, updateBoard.getCategory(), updateBoard.getTitle(), updateBoard.getContents(), updateBoard.getUpBoardImageUrl());
        return "redirect:/board";
    }

    // 게시글 삭제
    @DeleteMapping("/board/{boardId}")
    public String deleteBoard(@PathVariable Long boardId, RedirectAttributes redirectAttributes) {
        boolean isDeleted = boardService.deleteBoard(boardId);

        if (isDeleted) {
            redirectAttributes.addFlashAttribute("message", "게시물이 성공적으로 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("message", "게시물을 찾을 수 없습니다.");
        }

        return "redirect:/board";
    }

    // 게시글 이미지 업로드 하기
    @PostMapping("/board/upload")
    public ResponseEntity<?> uploadBoardFile(@RequestParam("BoardFile") MultipartFile image) throws IOException {
        System.out.println("1");

        String boardFileName = s3Service.upload(image);

        System.out.println("2");
        Board board = new Board();

        System.out.println("3");
        board.setBoardImageUrl(boardFileName);

        System.out.println("boardFileName" + boardFileName);
        boardRepository.save(board);

        return ResponseEntity.ok().build();
    }



}
