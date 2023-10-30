package com.example.board.memberController.controller;


import com.example.board.memberController.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

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
    public String mainHome(Model model, HttpSession session) {

        // 세션에서 현재 사용자 정보 가져오기
        Member member = (Member) session.getAttribute("sessionScope.nickName");

        // 모델에 사용자 정보 추가
        model.addAttribute("member", member);

        return "mainHome";
    }

    // myPage 이동
    @GetMapping("/myPage")
    public String myPage(Model model, HttpSession session) {
        // 세션에서 현재 사용자 정보 가져오기
        Member member = (Member) session.getAttribute("sessionScope.nickName");

        // 모델에 사용자 정보 추가
        model.addAttribute("member", member);

        return "myPage";
    }


    // logout
    @GetMapping("/logout")
    public String logout() {
        return "home";
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



    @GetMapping("/editNickName")
    public String editNickNamePage(Model model, HttpSession session) {

        // 세션에서 현재 사용자 정보 가져오기
        Member member = (Member) session.getAttribute("sessionScope.nickName");

        // 모델에 사용자 정보 추가
        model.addAttribute("member", member);

        return "editNickName";
    }

    //updatePassword
    @GetMapping("/updatePassword")
    public String updatePassword() {
        return "updatePassword";
    }

    // 회원탈퇴 페이지로 이동
    @GetMapping("/withdraw")
    public String withdraw(Model model, HttpSession session) {
        // 세션에서 현재 사용자 정보 가져오기
        Member member = (Member) session.getAttribute("sessionScope.nickName");

        // 모델에 사용자 정보 추가
        model.addAttribute("member", member);
        return "withdraw";
    }

}
