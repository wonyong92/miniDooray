package com.example.taskapi.domain;

import com.example.taskapi.annotation.ProjectStatusSubset;
import com.example.taskapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class ProjectUpdateRequest {
    @NotEmpty(message = "projectId must not empty")
    private String name;
    @NotNull(message = "projectId must not null")
    @ProjectStatusSubset(anyOf = {Project.Status.TERMINATION, Project.Status.ACTIVATE, Project.Status.DORMANCY})
    private Project.Status status;
}
