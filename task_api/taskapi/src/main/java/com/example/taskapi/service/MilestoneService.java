package com.example.taskapi.service;

import com.example.taskapi.domain.MilestoneAuthReadResponseDto;
import com.example.taskapi.domain.MilestoneCreateUpdateRequest;
import com.example.taskapi.domain.MilestoneCUDResponseDto;
import com.example.taskapi.domain.MilestoneReadResponseDto;

public interface MilestoneService {
    MilestoneCUDResponseDto createMilestone(MilestoneCreateUpdateRequest milestoneCreateUpdateRequest);
    MilestoneReadResponseDto readMilestone(Integer milestoneId);
    MilestoneCUDResponseDto updateMilestone(MilestoneCreateUpdateRequest milestoneCreateUpdateRequest, Integer milestoneId);
    MilestoneCUDResponseDto deleteMilestone(Integer milestoneId);
    MilestoneAuthReadResponseDto readAuthMilestone(Integer milestoneId);
}
