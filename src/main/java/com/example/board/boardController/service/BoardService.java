package com.example.board.boardController.service;

import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.entity.ReactionEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BoardService {





     // 게시글 생성
     Long createBoard(CreateBoardDto createBoardDto,  Optional<Member> memberOptional);

     Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents);

     Board findOne(Long boardId);

     boolean deleteBoard(Long boardId);

     List<Board> getAllBoards(); //  게시글 목록을 가져오는 메서드

     Board getBoardById(Long boardId); // 게시물 ID로 게시물을 가져오는 메서드

     // 좋아요 / 싫어요
     Board toggleReaction(Long boardId, Long id, ReactionEnum reactionEnum);

     Board saveBoard(Board board);

}
