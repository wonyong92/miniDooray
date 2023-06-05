package com.example.taskapi.controller;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.domain.ProjectDto;
import com.example.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/projects")
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.findAllProjectDto();
    }

    @GetMapping("/{projectId}")
    public ProjectDto getProject(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.findProjectDtoById(projectId);
    }

    @GetMapping("/{projectId}/members")
    public List<MemberDto> getProjectMembers(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.findAllMembersById(projectId);
    }

}
