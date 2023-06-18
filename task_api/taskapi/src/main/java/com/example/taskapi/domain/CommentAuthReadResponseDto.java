package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentAuthReadResponseDto {
    private Integer commentId;
    private String writerId;
    private Integer projectId;
}
