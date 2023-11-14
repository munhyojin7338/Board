package com.example.board.memberController.controller;

import com.example.board.memberController.dto.MemberLoginRequestDto;
import com.example.board.memberController.dto.MemberSignUpRequestDto;
import com.example.board.memberController.entity.Member;
import com.example.board.memberController.jwt.TokenInfo;
import com.example.board.memberController.memberService.MemberService;
import com.example.board.memberController.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
@ExtendWith(SpringExtension.class):
JUnit 5에서 사용되는 어노테이션으로, Spring 테스트 컨텍스트를 확장합니다

@SpringBootTest: 통합 테스트를 위한 어노테이션으로,
Spring Boot 애플리케이션 컨텍스트를 로드합니다. 이는 전체 애플리케이션을 테스트할 때 사용

@AutoConfigureMockMvc: 테스트 시 MockMvc를 자동으로 설정합니다.
MockMvc는 가짜 HTTP 요청을 만들어 컨트롤러를 호출하고 응답을 확인하는 데 사용됩니다.

@Mock :주로 테스트 코드에서 사용됩니다.

@Autowired: 필드 주입을 통해 MockMvc, MemberRepository, ObjectMapper 등을 주입합니다.
Spring이 자동으로 필요한 빈을 찾아서 주입하게 됩니다.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signupTest() throws Exception {
        //Given
        MemberSignUpRequestDto signUpRequestDto = MemberSignUpRequestDto.builder()
                .email("test@example.com")
                .password("1234")
                .nickName("testUser")
                .phone("01012345678")
                .build();

        //When
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequestDto)))
                .andReturn();

        //Then
        // 데이터베이스에서 실제로 회원이 생성되었는지 확인
        //Then
        int status = mvcResult.getResponse().getStatus();
        if (status == 200 || status == 201) { // 성공적인 상태 코드 (200 OK, 201 Created 등)
            // 데이터베이스에서 실제로 회원이 생성되었는지 확인
            Member saveMember = memberRepository.findByEmail("test@example.com")
                    .orElse(null);
            assertThat(saveMember).isNotNull();
            assertThat(saveMember.getEmail()).isEqualTo("test@example.com");
            assertThat(saveMember.getPassword()).isEqualTo("1234");
            assertThat(saveMember.getNickName()).isEqualTo("testUser");
            assertThat(saveMember.getPhone()).isEqualTo("01012345678");
        } else if (status == 400) { // 클라이언트 에러 (Bad Request 등)
            System.out.println("Response content: " + mvcResult.getResponse().getContentAsString());
        }
    }

    @Test
    void loginTest() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Given
        MemberLoginRequestDto loginRequestDto = MemberLoginRequestDto.builder()
                .email("test@example.com")
                .password("1234")
                .build();

        Member testMember = Member.builder()
                .email("test@example.com")
                .password("1234")
                .build();

        when(memberRepository.findByEmail(loginRequestDto.getEmail())).thenReturn(Optional.of(testMember));
        when(memberService.login(any(), any())).thenReturn(TokenInfo.builder().build());

        // Create a MockMvc instance
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();

        // When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", loginRequestDto.getEmail())
                        .param("password", loginRequestDto.getPassword()))
                .andReturn();

        // Then
        System.out.println("Response: " + result.getResponse().getContentAsString());
        System.out.println("View name: " + result.getModelAndView().getViewName());

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("email", loginRequestDto.getEmail())
                        .param("password", loginRequestDto.getPassword()))
                .andExpect(MockMvcResultMatchers.view().name("emailFail"))
                .andReturn();
    }


}