package com.example.taskapi.domain;

import com.example.taskapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProjectUpdateResponseDto {
    private Integer projectId;
    private String name;
    private Project.Status status;
}
