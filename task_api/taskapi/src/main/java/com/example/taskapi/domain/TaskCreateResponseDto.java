package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class TaskCreateResponseDto {
    private Integer taskId;
    private Integer projectId;

}
