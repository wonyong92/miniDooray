package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TaskReadResponseDto {
    private Integer taskId;
    private String title;
    private String writerId;

}
