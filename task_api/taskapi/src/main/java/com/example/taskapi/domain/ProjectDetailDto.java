package com.example.taskapi.domain;


import com.example.taskapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class ProjectDetailDto {
    private Integer projectId;
    private String name;
    private Project.Status status;
    private List<MemberDto> members;

    private List<MilestoneDto> milestones;
    private List<TagDto> tags;
    private List<TaskDto> tasks;

}
