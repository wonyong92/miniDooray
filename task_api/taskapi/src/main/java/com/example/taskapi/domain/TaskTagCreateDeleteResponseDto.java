package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TaskTagCreateDeleteResponseDto {
    private Integer taskId;
    private List<Integer> tagIds;
}
