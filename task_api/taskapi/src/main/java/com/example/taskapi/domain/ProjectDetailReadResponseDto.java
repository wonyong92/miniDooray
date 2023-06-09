package com.example.taskapi.domain;


import com.example.taskapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ProjectDetailReadResponseDto {
    private Integer projectId;
    private String name;
    private Project.Status status;
    private List<MemberReadResponseDto> members;
    private List<MilestoneReadResponseDto> milestones;
    private List<TagReadResponseDto> tags;
    private List<TaskReadResponseDto> tasks;

}
