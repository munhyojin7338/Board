package com.example.board.memberController.controller.kakao;

import com.example.board.memberController.entity.Member;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@Slf4j
public class KakaoController {

    private final KakaoService kakaoService;
    private final MemberRepository memberRepository;

    @GetMapping("/kakaoPage")
    public String kakaoLogin(@RequestParam(value = "code", required = false) String code
            , HttpServletResponse response
            , HttpServletRequest request) {
        if (code != null) {
            // 카카오 API에서 토큰 요청
            KakaoToken kakaoToken = kakaoService.requestToken(code);

            // 카카오 API에서 사용자 정보 요청
            Member member = kakaoService.requestUser(kakaoToken.getAccess_token());

            // 고유 ID를 Member DB에 저장
            if (member != null) {
                Long kakaoId = member.getKakaoId();
                Optional<Member> existingMemberOptional = memberRepository.findByKakaoId(kakaoId);

                if (existingMemberOptional.isPresent()) {
                    // 이미 존재하는 회원이면 업데이트
                    Member existingMember = existingMemberOptional.get();
                    existingMember.setKakaoId(kakaoId);
                    memberRepository.save(existingMember);
                } else {
                    // 새로운 회원일 경우, Member 엔티티에 저장
                    memberRepository.save(member);
                }
            }

            // 세션에 사용자 닉네임 저장
            HttpSession session = request.getSession();
            session.setAttribute("nickName", member.getNickName());
            session.setAttribute("memberId", member.getId()); // memberId를 세션에 추가

            return "redirect:/mainHome"; // 로그인 성공 후 이동할 페이지
        } else {
            // 코드가 없는 경우에 대한 처리 (에러 페이지 또는 다른 처리)
            return "redirect:/error"; // 에러 페이지로 리다이렉트
        }
    }

}
