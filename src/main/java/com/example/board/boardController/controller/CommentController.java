package com.example.board.boardController.controller;

import com.example.board.boardController.dto.CommentDto;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.Comment;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.repository.CommentRepository;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;



    @PostMapping("/comments/{boardId}")
    public ResponseEntity<String> createComment(@PathVariable Long boardId
            , @RequestBody CommentDto commentDto) {

        // 게시글 및 작성자 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        System.out.println("2");
        System.out.println(board);
        System.out.println("3");
        System.out.println(board.getMember());
        Member member = memberRepository.findByNickName(board.getMember().getNickName()).orElseThrow(() -> new RuntimeException("sdfsdf"));

        Comment comment = Comment.builder()
                .board(board)
                .member(member)
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
        board.addComment(comment); // 댓글을 게시글에 추가

        return ResponseEntity.ok("댓글이 작성되었습니다.");
    }

}
