package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TaskDto {
    private Integer taskId;
    private String title;
    private String writerId;

}
