package com.example.taskapi.controller;

import com.example.taskapi.domain.MilestoneAuthReadResponseDto;
import com.example.taskapi.domain.MilestoneCUDResponseDto;
import com.example.taskapi.domain.MilestoneCreateUpdateRequest;
import com.example.taskapi.domain.MilestoneReadResponseDto;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/milestones")
public class MilestoneRestController {
    private final MilestoneService milestoneService;


    @GetMapping("/{milestoneId}")
    public MilestoneReadResponseDto readMilestone(@PathVariable(name = "milestoneId") Integer milestoneId) {
        return milestoneService.readMilestone(milestoneId);
    }

    @PutMapping("/{milestoneId}")
    public MilestoneCUDResponseDto updateMilestone(@Validated @RequestBody MilestoneCreateUpdateRequest milestoneCreateUpdateRequest,
                                                   BindingResult bindingResult,
                                                   @PathVariable(name = "milestoneId") Integer milestoneId) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return milestoneService.updateMilestone(milestoneCreateUpdateRequest, milestoneId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MilestoneCUDResponseDto createMilestone(@Validated @RequestBody MilestoneCreateUpdateRequest milestoneCreateUpdateRequest,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return milestoneService.createMilestone(milestoneCreateUpdateRequest);
    }

    @DeleteMapping("/{milestoneId}")
    public MilestoneCUDResponseDto deleteMilestone(@PathVariable(name = "milestoneId") Integer milestoneId) {
        return milestoneService.deleteMilestone(milestoneId);
    }

    @GetMapping("/auth/{milestoneId}")
    public MilestoneAuthReadResponseDto readAuthMilestone(@PathVariable(name = "milestoneId") Integer milestoneId) {
        return milestoneService.readAuthMilestone(milestoneId);
    }
}
