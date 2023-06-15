package com.example.board.boardController.service.Impl;

import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.service.BoardService;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;



    @Override
    @Transactional
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    @Transactional
    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
    }


    @Override
    @Transactional
    public Board getBoardByIdAndMember(Long boardId, String memberNickname) {
        Member member = memberRepository.findByNickName(memberNickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        return boardRepository.findByIdAndMember(boardId, member)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없거나 권한이 없습니다."));
    }



    @Override
    @Transactional
    public Long board(Long id
            , CategoryEnum category
            , String title
            , String contents) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        Board board = new Board();
        board.createBoard(member, category, title, contents);
        boardRepository.save(board);

        return board.getId();
    }


    @Override
    @Transactional
    public Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시물을 찾을 수 없습니다."));

        board.setCategory(category);
        board.setTitle(updatedTitle);
        board.setContents(updatedContents);
        board.setUpdateDate(LocalDateTime.now());

        return board.getId();
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        board.getMember().getBoardList().removeIf(targetBoard -> targetBoard.equals(board));


        boardRepository.deleteById(boardId);
    }

    @Override
    public Board findOne(Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
    }

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }




}
