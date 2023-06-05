package com.example.taskapi.domain;


import com.example.taskapi.entity.ProjectMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class MemberDto {
    private String memberId;
    private ProjectMember.Role role;

}
