package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
@Getter
@AllArgsConstructor
public class ProjectCreateRequest {
    @NotEmpty(message = "name must not empty!")
    private String name;
    @NotEmpty(message = "adminId must not empty!")
    private String adminId;
}
