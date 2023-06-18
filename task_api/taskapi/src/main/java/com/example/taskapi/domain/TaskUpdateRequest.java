package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
public class TaskUpdateRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
}
