package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Getter
public class TaskCreateRequest {
    @NotEmpty(message = "title must not empty")
    private String title;
    @NotEmpty(message = "content must not empty")
    private String content;
    @NotEmpty(message = "writerId must not empty")
    private String writerId;
    @NotNull(message = "projectId must not empty")
    @Positive(message = "projectId must positive")
    private Integer projectId;
}
