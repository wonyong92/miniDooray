package com.example.taskapi.controller;

import com.example.taskapi.domain.ProjectDto;
import com.example.taskapi.entity.Project;
import com.example.taskapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectRestController {

    private final ProjectService projectService;

    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects() {
        return projectService.findAllProjectDto();
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDto getProject(@PathVariable(name = "projectId") Integer projectId) {
        return projectService.findProjectDtoById(projectId);
    }

}
