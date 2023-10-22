package com.example.board.memberController.repository;


import com.example.board.memberController.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);
    Optional<Member> findByKakaoId(Long kakaoId);

}
