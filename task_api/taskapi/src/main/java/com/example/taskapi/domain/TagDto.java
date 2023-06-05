package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TagDto {
    private Integer tagId;
    private String name;
}
