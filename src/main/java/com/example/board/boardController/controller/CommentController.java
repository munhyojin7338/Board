package com.example.board.boardController.controller;

import com.example.board.boardController.dto.CommentDto;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.Comment;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.repository.CommentRepository;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;


    // 댓글 생성
    @PostMapping("/comments/{boardId}")
    public ResponseEntity<?> createComment(@PathVariable("boardId") Long boardId
            , @RequestBody CommentDto commentDto
            , HttpSession session) {

        System.out.println("게시글 ID는 " + boardId);

        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Long memberId = (Long) session.getAttribute("memberId");
        System.out.println("memberId 는 " + memberId);

        Long KakaoId = (Long) session.getAttribute("KakaoId");
        System.out.println("KakaoId 는 " + KakaoId);
        Member member = null;
        if (KakaoId != null) {
            Optional<Member> memberOptional = memberRepository.findByKakaoId(KakaoId);
            if (memberOptional.isPresent()) {
                member = memberOptional.get();
                System.out.println("KakaoID 넣는곳");
            } else {
                System.out.println("가입된 회원이 없습니다");
            }
        } else if (memberId != null) {
            Optional<Member> memberIdOptional = memberRepository.findById(memberId);
            if (memberIdOptional.isPresent()) {
                member = memberIdOptional.get();
                System.out.println("memberId 넣기");
            } else {
                System.out.println("가입된 회원이 없습니다");
            }
        } else {
            System.out.println("KakaoId와 memberId가 모두 null입니다");
        }

        System.out.println("0");
        Comment comment = Comment.builder()
                .board(board)
                .member(member)
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now()) // 현재 시간으로 설정 (서버의 시간 기준)
                .build();

        System.out.println("comment는 : " + comment);

        System.out.println("1");
        commentRepository.save(comment);


        System.out.println("2");
        return ResponseEntity.ok(comment);
    }


    // 게시글 댓글 리스트 조회하는 Controller
    @GetMapping("/comment/list")
    public ResponseEntity<?> getCommentList(@RequestParam("bid") Long boardId) {
        // 게시글에 해당하는 댓글 리스트 조회
        List<Comment> comments = commentRepository.findByBoardId(boardId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            CommentDto commentDto = new CommentDto(i + 1 , comment.getMember().getNickName(), comment.getContent(), comment.getCreatedAt());
            commentDtos.add(commentDto);
        }

        return ResponseEntity.ok().body(commentDtos);
    }



    // 댓글 삭제
    @DeleteMapping("/comments/delete/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {

        // 댓글 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 댓글 삭제
        commentRepository.delete(comment);

        return ResponseEntity.ok().build();
    }


}
