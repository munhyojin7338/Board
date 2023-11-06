package com.example.board.memberController.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequestDto {

    @Email
    @NotBlank(message = "이메일 항목은 필수 입니다.")
    private String email;

    @NotBlank(message = "password 항목은 필수 입니다.")
    private String password;

}