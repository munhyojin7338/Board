package com.example.board.memberController.controller;

import com.example.board.memberController.dto.MemberLoginRequestDto;
import com.example.board.memberController.dto.MemberSignUpRequestDto;
import com.example.board.memberController.dto.MemberUpdateNickNameDto;
import com.example.board.memberController.dto.MemberUpdatePasswordDto;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.exception.ResponseError;
import com.example.board.memberController.jwt.TokenInfo;
import com.example.board.memberController.memberService.LoginCheckService;
import com.example.board.memberController.memberService.MemberService;
import com.example.board.memberController.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    private final LoginCheckService loginCheckService;
    private final MemberService memberService;


    @PostMapping("/signup")
    public String signup(@Valid MemberSignUpRequestDto signUpRequestDto) {

        boolean isEmailDuplicated = loginCheckService.checkEmail(signUpRequestDto.getEmail());

        boolean isNickNameDuplicated = loginCheckService.checkNickName(signUpRequestDto.getNickName());

        if (isEmailDuplicated) {
            // 중복된 이메일이 존재하므로 회원가입을 막음
            // 필요한 처리를 수행하고 회원가입 페이지로 되돌림
            return "redirect:/emailFail";
        }

        if (isNickNameDuplicated) {
            // 중복된 닉네임 존재하므로 회원가입을 막음

            return "redirect:/nickNameFail";
        }

        Member member = Member.builder()
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .nickName(signUpRequestDto.getNickName())
                .phone(signUpRequestDto.getPhone())
                .localDateTime(LocalDateTime.now())
                .build();
        memberRepository.save(member);
        System.out.println(member);

        return "redirect:/success";
    }

    // 로그인
    @PostMapping("/login")
    public ModelAndView login(@Valid MemberLoginRequestDto memberLoginRequestDto, Errors errors, HttpServletRequest request, HttpServletResponse response) {
        List<ResponseError> responseErrorList = new ArrayList<>();

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach((e) -> responseErrorList.add(ResponseError.of((FieldError) e)));
            return new ModelAndView("emailFail", HttpStatus.BAD_REQUEST)
                    .addObject("errors", responseErrorList);
        }

        Optional<Member> optionalMember = memberRepository.findByEmail(memberLoginRequestDto.getEmail());


        if (optionalMember.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorMessage", "가입되지 않은 E-MAIL 입니다.");
            return modelAndView;
        }

        Member member = optionalMember.get();

        if (!memberLoginRequestDto.getPassword().equals(member.getPassword())) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorMessage", "비밀번호가 틀렸습니다, 다시 확인해주세요!");
            return modelAndView;
        }

        TokenInfo tokenInfo = memberService.login(member.getEmail(), member.getPassword());

        // JWT 토큰을 쿠키에 저장
        Cookie jwtCookie = new Cookie("jwtToken", tokenInfo.getAccessToken());
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        System.out.println(jwtCookie);

        HttpSession session = request.getSession();
        session.setAttribute("nickName", member.getNickName());
        System.out.println(session);

        return new ModelAndView(new RedirectView("/mainHome"));

    }


    // 회원가입 시 nickName 증복 검사
    @GetMapping("/check/nickName/{nickName}")
    public ResponseEntity<Boolean> checkByNickName(@PathVariable String nickName) {
        return ResponseEntity.ok(loginCheckService.checkNickName(nickName));
    }

    // 회원가입 시 email 증복 검사
    @GetMapping("/check/email/{email}")
    public ResponseEntity<Boolean> checkByEmail(@PathVariable String email) {
        return ResponseEntity.ok(loginCheckService.checkEmail(email));
    }

    // 닉네임 수정
    @RequestMapping(value = "/updateNickName/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<Member> updateMemberNickName(
            @PathVariable Long id,
            @RequestBody @Valid MemberUpdateNickNameDto updateDto) {

        Member updateMemberNickName = memberService.updateMemberNickName(id, updateDto);

        return ResponseEntity.status(HttpStatus.OK).body(updateMemberNickName);
    }


    //myPage password 수정
    @RequestMapping(value = "/updatePassword/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<Member> updatePassword(@PathVariable Long id,
                                                 @RequestBody @Valid MemberUpdatePasswordDto memberUpdatePasswordDto) {

        Member updatedMemberPassword = memberService.updatePassword(id, memberUpdatePasswordDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedMemberPassword);
    }


}