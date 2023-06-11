package com.example.taskapi.controller;

import com.example.taskapi.domain.TaskMilestoneCreateDeleteRequest;
import com.example.taskapi.domain.TaskMilestoneCreateDeleteResponseDto;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.TaskMilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskMilestones")
public class TaskMilestoneRestController {
    private final TaskMilestoneService taskMilestoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskMilestoneCreateDeleteResponseDto createTaskMilestone(@RequestBody @Validated TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest,
                                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return taskMilestoneService.createTaskMilestone(taskMilestoneCreateDeleteRequest);
    }

    @DeleteMapping
    public TaskMilestoneCreateDeleteResponseDto deleteTaskMilestone(@RequestBody @Validated TaskMilestoneCreateDeleteRequest taskMilestoneCreateDeleteRequest,
                                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return taskMilestoneService.deleteTaskMilestone(taskMilestoneCreateDeleteRequest);
    }
}
