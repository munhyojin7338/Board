package com.example.board.boardController.entity;

import com.example.board.memberController.entity.Member;
import com.example.board.memberController.entity.Reaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id; // pk

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String contents; // 게시글 내용

    @Column
    private String boardImageUrl; // 게시판 이미지 업로드 로직

    private LocalDateTime createDate; // 생성날짜

    private LocalDateTime updateDate; // 수정날짜

    @Column
    private Integer likes = 0; // 좋아요 수

    @Column
    private Integer  dislikes; // 싫어요 수

    @Column
    private Integer  views; // 조회수

    // 좋아요 수 증가
    public void incrementLikes() {
        if (this.likes == null) {
            this.likes = 1;
        } else {
            this.likes++;
        }
    }

    // 좋아요 수 감소
    public void decrementLikes() {
        if (this.likes == null || this.likes <= 0) {
            this.likes = 0;
        } else {
            this.likes--;
        }
    }

    // 싫어요 수 증가
    public void incrementDislikes() {
        if (this.dislikes == null) {
            this.dislikes = 1;
        } else {
            this.dislikes++;
        }
    }

    // 싫어요 수 감소
    public void decrementDislikes() {
        if (this.dislikes == null || this.dislikes <= 0) {
            this.dislikes = 0;
        } else {
            this.dislikes--;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryEnum category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaction> reactions = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBoard(this);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setBoard(null);
    }


    public void createBoard(Member member, CategoryEnum category, String title, String contents) {
        this.member = member;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
        this.member.getBoardList().add(this);
    }

}
