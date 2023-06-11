package com.example.taskapi.controller;

import com.example.taskapi.domain.TaskTagCreateDeleteResponseDto;
import com.example.taskapi.domain.TaskTagCreateDeleteRequest;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskTags")
public class TaskTagRestController {
    private final TaskTagService taskTagService;

    @PostMapping
    public TaskTagCreateDeleteResponseDto createTaskTag(@RequestBody @Validated TaskTagCreateDeleteRequest taskTagCreateDeleteRequest,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return taskTagService.createTaskTag(taskTagCreateDeleteRequest);
    }

    @DeleteMapping
    public TaskTagCreateDeleteResponseDto deleteTaskTag(@RequestBody @Validated TaskTagCreateDeleteRequest taskTagCreateDeleteRequest,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return taskTagService.deleteTaskTag(taskTagCreateDeleteRequest);
    }
}
