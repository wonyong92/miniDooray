package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentReadResponseDto {
    private Integer commentId;
    private String writerId;
    private String content;
    private String modifiedAt;
}
