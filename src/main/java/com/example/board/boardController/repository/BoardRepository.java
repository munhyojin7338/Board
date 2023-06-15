package com.example.board.boardController.repository;

import com.example.board.boardController.entity.Board;
import com.example.board.memberController.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByIdAndMember(Long boardId, Member member);

}
