package com.example.board.memberController.controller;

import com.example.board.S3.S3Service;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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

    private final S3Service s3Service;

    //  회원가입
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
    public ModelAndView login(@Valid MemberLoginRequestDto memberLoginRequestDto
            , Errors errors
            , HttpServletResponse response
            , HttpServletRequest request) {
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
        Cookie idCookie = new Cookie("myId", member.getId().toString());

        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        response.addCookie(idCookie);


        // 세션에 사용자 닉네임 저장
        HttpSession session = request.getSession();
        session.setAttribute("nickName", member.getNickName());
        session.setAttribute("memberId", member.getId());
        session.setAttribute("kakaoId", member.getKakaoId());

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


    // editNickName 닉네임 수정
    @PostMapping("/editNickName/{id}")
    public String editNickName(@PathVariable Long id
            , @ModelAttribute MemberUpdateNickNameDto updateDto)  {
        try {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

            member.setNickName(updateDto.getUpdateNickName());
            member.setUpdateDate(LocalDateTime.now());

            System.out.println("member" + member);

            memberRepository.save(member);

            // 성공적으로 업데이트되었음을 나타내는 메시지를 반환
            return "redirect:/myPage";
        } catch (Exception e) {
            e.printStackTrace();
            // 업데이트에 실패한 경우 에러 메시지를 반환
            return "redirect:/mainHome";
        }
    }

    //myPage password 수정
    @PostMapping("/updatePassword/{id}")
    public ResponseEntity<Member> updatePassword(@PathVariable Long id,
                                                 @RequestBody @Valid MemberUpdatePasswordDto memberUpdatePasswordDto) {

        Member updatedMemberPassword = memberService.updatePassword(id, memberUpdatePasswordDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedMemberPassword);
    }

    // myPage 회원 프로필 이미지 업로드
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("proFile") MultipartFile proFile
            , HttpSession session) throws IOException {

        // 세션에서 현재 사용자 정보 가져오기
        Long memberId = (Long) session.getAttribute("memberId");
        Long kakaoId = (Long) session.getAttribute("kakaoId");

        if (memberId != null || kakaoId != null) {
            Optional<Member> memberOptional = null;

            if (memberId != null) {
                memberOptional = memberRepository.findById(memberId);
            } else if (kakaoId != null) {
                memberOptional = memberRepository.findByKakaoId(kakaoId);
            }

            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();

                String fileName = s3Service.upload(proFile);
                member.setImageUrl(fileName);
                memberRepository.save(member); // 이미지 URL 업데이트를 위해 저장

                // 이미지 업로드 후 마이페이지로 리디렉션
                return "redirect:/myPage";
            }
        }
// 이미지 업로드 후 마이페이지로 리디렉션
        return "redirect:/myPage";

    }


    // profile 이미지 삭제 하는 로직
    @DeleteMapping("/delete/imageFile/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("회원 정보가 없습니다"));

        String imageUrl = member.getImageUrl();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            s3Service.delete(imageUrl);
        }

        member.setImageUrl(null);
        memberRepository.save(member);

        return ResponseEntity.ok().build();
    }

    // 회원정보 삭제
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            // 해당 ID를 가진 회원 정보를 찾습니다.
            Optional<Member> optionalMember = memberRepository.findById(id);

            if (optionalMember.isPresent()) {
                Member member = optionalMember.get();
                // 회원 정보 삭제
                memberRepository.delete(member);
                return ResponseEntity.ok().body("회원이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 삭제 중 오류가 발생했습니다.");
        }
    }
}