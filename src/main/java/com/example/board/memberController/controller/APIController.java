package com.example.board.memberController.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
@Slf4j
public class APIController {

    // 메인 화면 표시
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // 회원가입 페이지 표시
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    // 회원가입 성공 페이지
    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    // 로그인 페이지 표시
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 로그인 성공하여 나온 mainPage
    @GetMapping("/mainHome")
    public String conditionalHome() {
        return "mainHome";
    }

    // logout
    @GetMapping("/logout")
    public String logout() {
        return "home";
    }

    // myPage
    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }

    // Fail
    @GetMapping("/emailFail")
    public String emailFail() {
        return "emailFail";
    }

    // Fail
    @GetMapping("/nickNameFail")
    public String nickName() {
        return "nickNameFail";
    }

    //updateNickName 페이지이동
    @GetMapping("/updateNickName")
    public String updateNickName() {
        return "updateNickName";
    }


    //updatePassword
    @GetMapping("/updatePassword")
    public String updatePassword() {
        return "updatePassword";
    }

}
