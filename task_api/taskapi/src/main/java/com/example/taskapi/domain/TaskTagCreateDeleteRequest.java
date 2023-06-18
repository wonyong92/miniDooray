package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@AllArgsConstructor
@Getter
public class TaskTagCreateDeleteRequest {
    @NotNull
    @Positive
    private Integer taskId;
    @NotEmpty
    private List<Integer> tagIds;
}
