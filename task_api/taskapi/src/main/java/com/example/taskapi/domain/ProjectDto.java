package com.example.taskapi.domain;

import com.example.taskapi.entity.Project;

public interface ProjectDto {
    Integer getProjectId();

    String getName();
    Project.Status getStatus();
}
