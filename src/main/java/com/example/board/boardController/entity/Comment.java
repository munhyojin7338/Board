package com.example.board.boardController.entity;

import com.example.board.memberController.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Member member;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updateDate;


    public Comment(Board board, Member member, String content) {
        this.board = board;
        this.member = member;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

}
