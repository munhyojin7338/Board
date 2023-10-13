package com.example.board.boardController.repository;

import com.example.board.boardController.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long boardId);
    List<Board> findByOrderByViewsDesc(); // 조회수 높은 순으로 게시글을 가져옴
}
