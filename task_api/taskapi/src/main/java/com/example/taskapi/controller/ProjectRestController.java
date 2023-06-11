package com.example.taskapi.controller;

import com.example.taskapi.domain.*;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/projects")
public class ProjectRestController {

    private final ProjectService projectService;


    @GetMapping("/{projectId}")
    public ProjectDetailReadResponseDto readProject(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.readProject(projectId);
    }

    @GetMapping("/{projectId}/members")
    public ProjectMemberReadResponseDto readProjectMembers(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.readProjectMembers(projectId);
    }

    @GetMapping("/members/{memberId}")
    public ProjNameForMemReadResponseDto readProjNameForMem(@PathVariable(name = "memberId") String memberId) {
        return projectService.readProjNamesForMem(memberId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectCreateResponseDto createProject(@RequestBody @Validated ProjectCreateRequest projectCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return projectService.createProject(projectCreateRequest);
    }

    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectUpdateResponseDto updateProject(@RequestBody @Validated ProjectUpdateRequest projectUpdateRequest, BindingResult bindingResult,
                                                  @PathVariable(name = "projectId") Integer projectId) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return projectService.updateProject(projectUpdateRequest, projectId);
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDeleteResponseDto deleteProject(@PathVariable(name = "projectId") Integer projectId) {

        return projectService.deleteProject(projectId);

    }
}
