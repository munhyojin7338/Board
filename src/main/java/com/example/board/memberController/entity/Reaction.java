package com.example.board.memberController.entity;

import com.example.board.boardController.entity.Board;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/*
 좋아요, 싫어요는 사용자가 게시글에 대한 반응을 표시하는 기능이므로, 사용자 정보와 게시글 정보를
 연결하는 테이블을 추가로 생성하고 관리하는게 일반적이다 -> Board 와 member 간의 다대다 관계를 별도의
 연결 테이블을 생성하고 관리 할 수 있다.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reaction {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @JsonIgnoreProperties("reactions")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private ReactionEnum reactionEnum;


}
