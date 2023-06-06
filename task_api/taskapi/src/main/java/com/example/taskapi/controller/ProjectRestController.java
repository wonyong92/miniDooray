package com.example.taskapi.controller;

import com.example.taskapi.domain.*;
import com.example.taskapi.exception.ValidationFailedException;
import com.example.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/projects")
public class ProjectRestController {

    private final ProjectService projectService;


    @GetMapping("/{projectId}")
    public ProjectDetailDto getProject(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.findProjectDtoById(projectId);
    }

    @GetMapping("/{projectId}/members")
    public List<MemberDto> getProjectMembers(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.findAllMembersById(projectId);
    }

    @GetMapping("/members/{memberId}")
    public ProjNameForMemDto getMemberInvolvedProjectNames(@PathVariable(name = "memberId") String memberId) {
        return projectService.findProjNamesByMemberId(memberId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectCreateResponseDto createProject(@RequestBody @Validated ProjectCreateRequest projectCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        return projectService.creatProject(projectCreateRequest);
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

        return new ProjectDeleteResponseDto(projectService.deleteProject(projectId));

    }
}
