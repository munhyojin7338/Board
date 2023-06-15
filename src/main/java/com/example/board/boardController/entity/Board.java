package com.example.board.boardController.entity;

import com.example.board.memberController.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryEnum category;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

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
