package com.example.board.boardController.entity;

import com.example.board.memberController.entity.Member;
import com.example.board.memberController.entity.Reaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonIgnore  // 순환 참조를 방지하기 위해 추가
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
    private Integer  views = 0; // 조회수



    // 조회수 증가
    public void incrementViews() {
        if (this.views == null) {
            this.views = 1;
        } else {
            this.views++;
        }
        System.out.println("incrementViews() 호출");
    }



    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryEnum category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("board")  // 순환 참조를 방지하기 위해 추가
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("board")
    private List<Reaction> reactions = new ArrayList<>();


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
