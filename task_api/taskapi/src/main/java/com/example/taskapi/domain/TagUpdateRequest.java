package com.example.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TagUpdateRequest {
    @NotEmpty
    private String name;

}
