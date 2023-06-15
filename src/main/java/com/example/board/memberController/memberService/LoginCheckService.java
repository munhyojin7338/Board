package com.example.board.memberController.memberService;

import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginCheckService {

    private final MemberRepository memberRepository;

    // email
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }


    // nickName
    public boolean checkNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }
}
