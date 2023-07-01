package com.example.board.memberController.controller.kakao;

import com.example.board.memberController.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;


    @RequestMapping("/kakaoPage")
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code
            , HttpServletRequest request) {
        if (code != null) { //카카오측에서 보내준 code가 있다면 출력합니다
            System.out.println("code = " + code);

            //추가됨: 카카오 토큰 요청
            KakaoToken kakaoToken = kakaoService.requestToken(code);
            log.info("kakoToken = {}", kakaoToken);

            Member member = kakaoService.requestUser(kakaoToken.getAccess_token());
            log.info("member 정보 = {}", member);

            // 세션에 사용자 닉네임 저장
            HttpSession session = request.getSession();
            session.setAttribute("nickName", member.getNickName());

        }

        return "mainHome";
    }
}
