package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskAuthReadResponseDto {
    private Integer taskId;
    private Integer projectId;
}
