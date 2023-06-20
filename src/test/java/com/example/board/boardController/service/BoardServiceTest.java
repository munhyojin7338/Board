package com.example.board.boardController.service;

import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.Category;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.repository.CategoryRepository;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntityManager em;

    @Test
    // board 생성
    public void Board() {
        // given
        Member member = new Member();
        member.setId(1L);
        memberRepository.save(member);

        Category category = new Category();
        category.setName(CategoryEnum.FOOTBALL);
        categoryRepository.save(category);

        Long memberId = member.getId();
        CategoryEnum categoryEnum = category.getName();

        // when
        Long boardId = boardService.board(memberId, categoryEnum, "test", "this is test");

        // then
    assertThat(boardId).isNotNull();
}


    @Test // 게시판 수정
    void updateBoard() {
        // given
        Member member = new Member();
        member.setId(1L);
        memberRepository.save(member);

        CategoryEnum categoryEnum = CategoryEnum.FOOTBALL;

        Long memberId = member.getId();

        Long boardId = boardService.board(memberId, categoryEnum, "test", "this is test");

        String updatedTitle = "updated test";
        String updatedContents = "this is updated test";

        // when
        boardService.updateBoard(boardId, categoryEnum, updatedTitle, updatedContents);


        // then
        Board findBoard = boardService.findOne(boardId);
        assertThat(findBoard.getTitle()).isEqualTo(updatedTitle);
        assertThat(findBoard.getContents()).isEqualTo(updatedContents);
    }

    @Test
    void deleteBoard () {
        // given
        Member member = new Member();
        member.setId(1L);
        memberRepository.save(member);

        Category category = new Category();
        category.setName(CategoryEnum.FOOTBALL);
        categoryRepository.save(category);

        Long memberId = member.getId();
        CategoryEnum categoryEnum = category.getName();


        Long boardId = boardService.board(memberId, categoryEnum, "title", "contents");

        // when
        boolean isDeleted = boardService.deleteBoard(boardId);

        // then
        assertThat(isDeleted).isTrue();
       }


}