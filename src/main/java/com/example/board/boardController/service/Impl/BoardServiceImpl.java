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

 /*
    JPA
    @Transactional(readOnly = true) : 조회용 메서드에 대해서는 @Transactional(readOnly = true) 를 설정함으로써
    성능상 이점을 얻을 수 있다

  */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    /*
    조회수 증가 , 게시물 수정을 위한 로직
     */
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
            board.createBoard(member, createBoardDto.getCategoryEnum(), createBoardDto.getTitle(), createBoardDto.getContents(), createBoardDto.getBoardImageUrl());
            boardRepository.save(board);
            return board.getId();
        } else {
            throw new IllegalArgumentException("찾을 수 없는 member 입니다.");
        }
    }

    // 게시글 수정
    @Override
    @Transactional
    public Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents, String upBoardImageUrl) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setCategory(category);
            board.setTitle(updatedTitle);
            board.setContents(updatedContents);
            board.setBoardImageUrl(upBoardImageUrl);
            board.setUpdateDate(LocalDateTime.now());

            return board.getId();
        } else {
            // 게시물을 찾을 수 없는 경우, 예외를 던지지 않고 다른 처리를 할 수 있습니다.
            // 예를 들어, 로깅하거나 기본값을 반환하거나 특정 응답을 생성할 수 있습니다.
            // 여기서는 null을 반환하도록 수정하였습니다.
            return null;
        }
    }

    /*
    게시글 삭제
     */
    @Override
    @Transactional
    public boolean deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다."));

        board.getMember().getBoardList().removeIf(targetBoard -> targetBoard.equals(board));


        boardRepository.deleteById(boardId);
        return true;
    }



    // 조회수 증가
    @Override
    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

    /*
    높은 조회수가 가장 위에 있게 하는 작업
    페이징 처리 하기
     */
    // 높은 조회수가 가장 위에 있게 하는 작업
    @Override
    public List<Board> getBoardsOrderByViewsDesc() {
        return boardRepository.findByOrderByViewsDesc();

    }


}
