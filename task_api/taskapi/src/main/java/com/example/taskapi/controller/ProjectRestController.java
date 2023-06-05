package com.example.taskapi.controller;

import com.example.taskapi.domain.MemberDto;
import com.example.taskapi.domain.ProjNameForMemDto;
import com.example.taskapi.domain.ProjectDetailDto;
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
    public List<ProjectDetailDto> getAllProjects() {
        return projectService.findAllProjectDto();
    }

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

}
