package com.example.board.memberController.entity;

import com.example.board.boardController.entity.Board;
import com.example.board.boardController.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
회원가입, 로그인(JWT토큰), 회원정보수정(닉네임, 비밀번호),
게시판 글쓰기, 댓글, 높은 조회수, 이미지파일 올리기
 */

/*
 기술 사용:
 JPA (entity, Id, GeneratedValue)
 @OneToMany, @Entity
 상속(implements),
 mySQL,
 lombok
 setter/getter,
 @Builder,
 @NoArgsConstructor, @AllArgsConstructor
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "id")
    private Long id;

    @Column
    private String email;

    @Column
    private String password;


    @Column
    private String nickName;

    @Column
    private String phone;

    // 회원가입 날짜
    @Column
    private LocalDateTime localDateTime;

    // 회원 정보 수정한 날짜
    @Column
    private LocalDateTime updateDate;

    // url 경로
    @Column
    private String imageUrl;

    // 카카오에서 받아온 고유 ID 값
    @Column
    private Long kakaoId;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}