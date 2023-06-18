package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentCreateUpdateResponseDto {
    private Integer commentId;
    private Integer taskId;
}
