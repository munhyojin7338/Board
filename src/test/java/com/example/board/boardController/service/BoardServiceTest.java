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
        member.setEmail("munhyojin7338@gmail.com");
        member.setPassword("1234");
        memberRepository.save(member);

        Category category = new Category();
        category.setName(CategoryEnum.FOOTBALL);
        categoryRepository.save(category);



        String nickName = member.getNickName();
        CategoryEnum categoryEnum = category.getName();


        // when
        Long boardId = boardService.board(nickName, categoryEnum, "test", "this is test");

        // then
        assertThat(boardId).isNotNull();
    }

    @Test// Id로 board 찾기 -> nickName 으로 Board
    void findById() {
        // given
        Member member = new Member();
        member.setEmail("munhyojin7338@gmail.com");
        member.setPassword("1234");
        memberRepository.save(member);

        Category category = new Category();
        category.setName(CategoryEnum.FOOTBALL);
        categoryRepository.save(category);;

        String nickName = member.getNickName();
        CategoryEnum categoryEnum = category.getName();
        Long boardId = boardService.board(nickName, categoryEnum, "test", "this is test");

        // when
        Board findBoard = boardService.findOne(boardId);

        // then
        assertThat(findBoard.getId()).isEqualTo(boardId);
        assertThat(findBoard.getMember().getNickName()).isEqualTo(nickName);
        assertThat(findBoard.getCategory().getName()).isEqualTo(categoryEnum);
        assertThat(findBoard.getTitle()).isEqualTo("test");
        assertThat(findBoard.getContents()).isEqualTo("this is test");
    }


//    @Test
// 회원으로 board 찾기
//    void findByMember() {
//        // given
//        Member member = new Member();
//        member.setEmail("munhyojin7338@gmail.com");
//        member.setNickName("오리너구리");
//        member.setPassword("ddd");
//        memberRepository.save(member);
//
//        Category category = new Category();
//        category.setName("Anony");
//        categoryRepository.save(category);
//
//        String nickName = member.getNickName();
//        Long categoryId = category.getId();
//
//        // when
//        boardService.board(nickName, categoryId, "test", "this is test");
//        boardService.board(nickName, categoryId, "test2", "this is 2nd test");
//
//        Member findMember = memberRepository.findByNickName(nickName)
//                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
//
//        List<Board> boardList = boardService.findByMember(findMember);
//
//
//        // then
//        assertThat(boardList.size()).isEqualTo(2);
//        for (Board board : boardList) {
//            System.out.println("board.getTitle() = " + board.getTitle());
//            assertThat(board.getMember().getNickName()).isEqualTo(nickName);
//        }
//    }

    @Test// board 전부 다 찾기
    void findAll() {
        // given
        Member member = new Member();
        member.setEmail("munhyojin7338@gmail.com");
        member.setPassword("ddd");
        memberRepository.save(member);

        Category category = new Category();
        category.setName(CategoryEnum.FOOTBALL);
        categoryRepository.save(category);;

        String nickName = member.getNickName();
        CategoryEnum categoryEnum = category.getName();

        // when
        boardService.board(nickName, categoryEnum, "test", "this is test");
        boardService.board(nickName, categoryEnum, "test2", "this is 2nd test");
        boardService.board(nickName, categoryEnum, "test3", "this is 3nd test");

        List<Board> all = boardService.findAll();

        // then
        assertThat(all.size()).isEqualTo(3);

    }




    @Test
    void updateBoard() {
        // given
        Member member = new Member();

        member.setEmail("munhyojin7338@gmail.com");
        member.setPassword("ddd");
        memberRepository.save(member);

        CategoryEnum categoryEnum = CategoryEnum.FOOTBALL;

        String nickName = member.getNickName();

        Long boardId = boardService.board(nickName, categoryEnum, "test", "this is test");

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
        member.setEmail("deleteBoard@gmail.com");
        member.setPassword("ddd");
        memberRepository.save(member);

        Category category = new Category();
        category.setName(CategoryEnum.FOOTBALL);
        categoryRepository.save(category);

        String nickName = member.getNickName();
        CategoryEnum categoryEnum = category.getName();


        Long boardId = boardService.board(nickName, categoryEnum, "title", "contents");


        List<Board> before = boardService.findAll();
        System.out.println("before = " + before);

        // when
        boardService.deleteBoard(boardId);
        List<Board> after = boardService.findAll();
        System.out.println("after = " + after);

        // then
        assertThat(after.size()).isEqualTo(before.size()-1);
       }


}