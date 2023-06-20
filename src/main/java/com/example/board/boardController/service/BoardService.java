package com.example.board.boardController.service;

import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {

     Long board(Long id, CategoryEnum category, String title, String contents);

     Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents);

     Board findOne(Long boardId);

     boolean deleteBoard(Long boardId);

     List<Board> getAllBoards(); //  게시글 목록을 가져오는 메서드
     Board getBoardById(Long boardId); // 게시물 ID로 게시물을 가져오는 메서드






}
