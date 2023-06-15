package com.example.board.memberController.repository;


import com.example.board.memberController.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String username);

    Optional<Member> findById(Long id);

    Optional<Member> findByNickName(String nickName);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);

}
