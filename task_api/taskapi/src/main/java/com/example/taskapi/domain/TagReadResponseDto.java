package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class TagReadResponseDto {
    private Integer tagId;
    private String name;
}
