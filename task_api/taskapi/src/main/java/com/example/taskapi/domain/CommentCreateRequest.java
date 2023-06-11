package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@AllArgsConstructor
@Getter
public class CommentCreateRequest {
    @NotNull
    @Positive
    private Integer taskId;
    @NotEmpty
    private String writerId;
    @NotEmpty
    private String content;
}
