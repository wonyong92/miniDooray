package com.example.taskapi.domain;


import com.example.taskapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Setter
public class ProjectDto {
    private Integer projectId;
    private String name;
    private Project.Status status;
    private String adminId;

    private List<MemberDto> members;

    private List<MilestoneDto> milestones;

    private List<TaskDto> tasks;

    public ProjectDto(Integer projectId, String name, Project.Status status, String adminId) {
        this.projectId = projectId;
        this.name = name;
        this.status = status;
        this.adminId = adminId;
    }
}
