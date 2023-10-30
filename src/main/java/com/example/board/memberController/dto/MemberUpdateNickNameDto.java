package com.example.board.memberController.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateNickNameDto {

    @NotBlank(message = "새로운 닉네임을 입력해주세요.")
    private String updateNickName;

}
