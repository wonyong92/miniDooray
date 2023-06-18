package com.example.taskapi.domain;

import com.example.taskapi.entity.Project;

public interface ProjectReadResponseDto {
    Integer getProjectId();
    String getName();
    Project.Status getStatus();
}
