package com.example.taskapi.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonPropertyOrder({"taskId", "projectId"})
@AllArgsConstructor
@Getter
public class TaskUpdateResponseDto {
    private Integer projectId;
    private Integer taskId;

}
