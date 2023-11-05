package com.example.board.memberController.repository;


import com.example.board.memberController.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 JPA JpaRepository를 상속하는 인터페이스에 메서드 이름만 적어놓으면
알아서 다 처리(구현체 생성, 쿼리문 구현 등)해주는 좋은 ORM이다.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByNickName(String nickName);
    Optional<Member> findByKakaoId(Long kakaoId);

}
