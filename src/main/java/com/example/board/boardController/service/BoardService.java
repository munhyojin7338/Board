package com.example.board.boardController.service;

import com.example.board.boardController.dto.CreateBoardDto;
import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.CategoryEnum;
import com.example.board.memberController.entity.Member;

import java.util.List;
import java.util.Optional;

public interface BoardService {


     // 게시글 생성
     Long createBoard(CreateBoardDto createBoardDto,  Optional<Member> memberOptional, Optional<Member> memberIdOptional);
     Long updateBoard(Long boardId, CategoryEnum category, String updatedTitle, String updatedContents, String UpBoardImageUrl);


     boolean deleteBoard(Long boardId); // 게시글 삭제

     List<Board> getAllBoards(); //  게시글 목록을 가져오는 메서드

     Board getBoardById(Long boardId); // 게시물 ID로 게시물을 가져오는 메서드

     Board saveBoard(Board board); // 게시글 조회수를 저장

     List<Board> getBoardsOrderByViewsDesc(); // 조회수 높은 순으로 게시글 목록을 가져옴

}
