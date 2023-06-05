package com.example.taskapi.service;

import com.example.taskapi.domain.*;
import com.example.taskapi.entity.Project;
import com.example.taskapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;

    private final CommentRepository commentRepository;
    @Override
    public List<ProjectDto> findAllProjectDto() {
        List<ProjectDto> allProjectDto = projectRepository.findAllProjectDto();

        for (ProjectDto projectDto : allProjectDto) {
            List<MemberDto> allProjectMembers = projectMemberRepository.findAllProjectMemberDtoByProjectId(projectDto.getProjectId());
            List<MilestoneDto> allMilestones = milestoneRepository.findAllMilestoneDtoByProjectId(projectDto.getProjectId());
            List<TaskDto> allTasks = taskRepository.findAllTaskDtoByProjectId(projectDto.getProjectId());
            for (TaskDto task : allTasks) {
                List<CommentDto> allCommentsByTask = commentRepository.findAllCommentDtoByTaskId(task.getTaskId());
                task.setComments(allCommentsByTask);
            }
            projectDto.setMembers(allProjectMembers);
            projectDto.setMilestones(allMilestones);
            projectDto.setTasks(allTasks);
        }
        return allProjectDto;
    }

    @Override
    public ProjectDto findProjectDtoById(Integer projectId) {
        ProjectDto projectDto = projectRepository.findProjectDtoByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("project not found"));
        List<MemberDto> allProjectMembers = projectMemberRepository.findAllProjectMemberDtoByProjectId(projectId);
        List<MilestoneDto> allMilestones = milestoneRepository.findAllMilestoneDtoByProjectId(projectId);
        List<TaskDto> allTasks = taskRepository.findAllTaskDtoByProjectId(projectId);
        for (TaskDto task : allTasks) {
            List<CommentDto> allCommentsByTask = commentRepository.findAllCommentDtoByTaskId(task.getTaskId());
            task.setComments(allCommentsByTask);
        }
        projectDto.setMembers(allProjectMembers);
        projectDto.setMilestones(allMilestones);
        projectDto.setTasks(allTasks);

        return projectDto;
    }
}
