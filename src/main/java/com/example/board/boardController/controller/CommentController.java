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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    // 댓글 생성
    @PostMapping("/comments/{boardId}")
    public ResponseEntity<String> createComment(@PathVariable Long boardId
            , @RequestBody CommentDto commentDto) {

        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Member member = memberRepository.findById(board.getMember().getId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .board(board)
                .member(member)
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);

        board.addComment(comment); // 댓글을 게시글에 추가

        return ResponseEntity.ok("댓글이 작성되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/comments/delete/{boardId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long boardId) {
        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다"));

        // 댓글 목록을 순회 하면서 댓글을 삭제하기 위해 호출하는 로직
        List<Comment> comments = board.getComments();

        // 댓글 삭제
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }

        return ResponseEntity.ok().build();
    }

}
