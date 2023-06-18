package com.example.taskapi.entity;

import com.example.taskapi.domain.MilestoneCreateUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Milestones")
@Getter
@ToString
@NoArgsConstructor
public class Milestone {

    @Id
    @Column(name = "milestone_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer milestoneId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "start_at", nullable = false)
    private LocalDate startAt;
    @Column(name = "end_at", nullable = false)
    private LocalDate endAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public Milestone(String name, LocalDate startAt, LocalDate endAt, Project project) {
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.project = project;
    }

    public void updateMilestoneWithDto(MilestoneCreateUpdateRequest milestoneCreateUpdateRequest) {
        this.name = milestoneCreateUpdateRequest.getName();
        this.startAt = milestoneCreateUpdateRequest.getStartAt();
        this.endAt = milestoneCreateUpdateRequest.getEndAt();
    }
}
