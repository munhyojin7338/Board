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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class CommentController {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    // 댓글 생성
    @PostMapping("/comments/{boardId}/{id}")
    public ResponseEntity<?> createComment(@PathVariable("boardId") Long boardId
            , @PathVariable Long id
            , @RequestBody CommentDto commentDto) {

        System.out.println(boardId);
        System.out.println(id);

        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .board(board)
                .member(member)
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now()) // 현재 시간으로 설정 (서버의 시간 기준)
                .build();
        System.out.println(comment);

        commentRepository.save(comment);

        return ResponseEntity.ok(comment);
    }

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

        return ResponseEntity.ok().body(comments);
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
