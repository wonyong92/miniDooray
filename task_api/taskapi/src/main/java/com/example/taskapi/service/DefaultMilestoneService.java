package com.example.taskapi.service;

import com.example.taskapi.domain.MilestoneAuthReadResponseDto;
import com.example.taskapi.domain.MilestoneCreateUpdateRequest;
import com.example.taskapi.domain.MilestoneCUDResponseDto;
import com.example.taskapi.domain.MilestoneReadResponseDto;
import com.example.taskapi.entity.Milestone;
import com.example.taskapi.entity.Project;
import com.example.taskapi.exception.NotFoundException;
import com.example.taskapi.repository.MilestoneRepository;
import com.example.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DefaultMilestoneService implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    @Override
    @Transactional
    public MilestoneCUDResponseDto createMilestone(MilestoneCreateUpdateRequest milestoneCreateUpdateRequest) {
        Project project = projectRepository.findById(milestoneCreateUpdateRequest.getProjectId())
                .orElseThrow(() -> new NotFoundException("project not found, projectId = " + milestoneCreateUpdateRequest.getProjectId()));
        Milestone milestone = new Milestone(milestoneCreateUpdateRequest.getName(), milestoneCreateUpdateRequest.getStartAt(),
                milestoneCreateUpdateRequest.getEndAt(), project);
        Milestone save = milestoneRepository.save(milestone);
        return new MilestoneCUDResponseDto(save.getProject().getProjectId(), save.getMilestoneId());
    }

    @Override
    public MilestoneReadResponseDto readMilestone(Integer milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone not found, milestoneId  = " + milestoneId));
        return new MilestoneReadResponseDto(milestone.getMilestoneId(),
                milestone.getName(), milestone.getStartAt(), milestone.getEndAt());
    }

    @Override
    @Transactional
    public MilestoneCUDResponseDto updateMilestone(MilestoneCreateUpdateRequest milestoneCreateUpdateRequest, Integer milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone not found, milestoneId = " + milestoneId));
        milestone.updateMilestoneWithDto(milestoneCreateUpdateRequest);
        return new MilestoneCUDResponseDto(milestone.getProject().getProjectId(), milestone.getMilestoneId());
    }

    @Override
    @Transactional
    public MilestoneCUDResponseDto deleteMilestone(Integer milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone not found, milestoneId = " + milestoneId));
        milestoneRepository.delete(milestone);
        return new MilestoneCUDResponseDto(milestone.getProject().getProjectId(), milestone.getMilestoneId());
    }

    @Override
    public MilestoneAuthReadResponseDto readAuthMilestone(Integer milestoneId) {
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("milestone not found, milestoneId = " + milestoneId));
        return new MilestoneAuthReadResponseDto(milestone.getMilestoneId(), milestone.getProject().getProjectId());
    }

}
