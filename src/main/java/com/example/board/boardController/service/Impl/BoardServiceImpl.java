package com.example.board.boardController.service.Impl;

import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.boardController.repository.BoardRepository;
import com.example.board.boardController.service.BoardService;
import com.example.board.memberController.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
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


    // 게시글 생성
    @Transactional
    @Override
    public Long createBoard(CreateBoardDto createBoardDto, Optional<Member> memberOptional, Optional<Member> memberIdOptional) {
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            Board board = new Board();
            board.createBoard(member, createBoardDto.getCategoryEnum(), createBoardDto.getTitle(), createBoardDto.getContents(), createBoardDto.getBoardImageUrl());
            boardRepository.save(board);
            return board.getId();
        } else if (memberIdOptional.isPresent()) {
            Member member = memberIdOptional.get();
            Board board = new Board();
            board.createBoard(member, createBoardDto.getCategoryEnum(), createBoardDto.getTitle(), createBoardDto.getContents(),  createBoardDto.getBoardImageUrl());
            boardRepository.save(board);
            return board.getId();
        } else {
            return null; // 또는 예외를 던지거나 다른 처리 방식을 선택
        }
    }

    // 게시글 수정
    @Override
    @Transactional
    public Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents, String UpBoardImageUrl) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시물을 찾을 수 없습니다."));

        board.setCategory(category);
        board.setTitle(updatedTitle);
        board.setContents(updatedContents);
        board.setBoardImageUrl(UpBoardImageUrl);
        board.setUpdateDate(LocalDateTime.now());

        return board.getId();
    }

    @Override
    @Transactional
    public boolean deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        board.getMember().getBoardList().removeIf(targetBoard -> targetBoard.equals(board));


        boardRepository.deleteById(boardId);
        return true;
    }

    @Override
    public Board findOne(Long boardId) {

        return boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));
    }



    // 조회수를 올리는 작업
    @Override
    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    // 높은 조회수가 가장 위에 있게 하는 작업
    @Override
    public List<Board> getBoardsOrderByViewsDesc() {
        return boardRepository.findByOrderByViewsDesc();

    }


}
