package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MemberDto {

    private String memberId;
    private String nickname;
    private String email;

}
