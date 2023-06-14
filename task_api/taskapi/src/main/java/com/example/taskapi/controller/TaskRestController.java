package com.example.taskapi.controller;

import com.example.taskapi.domain.*;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tasks")
public class TaskRestController {
    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public TaskDetailReadResponseDto readTask(@PathVariable(name = "taskId") Integer taskId) {
        return taskService.readTask(taskId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskCreateResponseDto createTask(@RequestBody @Validated TaskCreateRequest taskCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return taskService.createTask(taskCreateRequest);
    }

    @PutMapping("/{taskId}")
    public TaskUpdateResponseDto updateTask(@PathVariable(name = "taskId") Integer taskId,
                                            @RequestBody @Validated TaskUpdateRequest taskUpdateRequest,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return taskService.updateTask(taskUpdateRequest, taskId);
    }

    @DeleteMapping("/{taskId}")
    public TaskDeleteResponseDto deleteTask(@PathVariable(name = "taskId") Integer taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping("/auth/{taskId}")
    public TaskAuthReadResponseDto readAuthTask(@PathVariable(name = "taskId") Integer taskId) {
        return taskService.readAuthTask(taskId);
    }
}
