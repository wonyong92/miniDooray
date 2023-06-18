package com.example.taskapi.domain;

import com.example.taskapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@AllArgsConstructor
@Getter
public class ProjectMemberReadResponseDto {
    private Integer projectId;
    private Project.Status status;
    private List<MemberReadResponseDto> members;
}
