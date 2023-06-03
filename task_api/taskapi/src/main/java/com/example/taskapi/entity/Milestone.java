package com.example.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Milestones")
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Milestone {

    @Id
    @Column(name = "milestone_id")
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

}
