package com.example.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TaskMilestones")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskMilestone {

    @EmbeddedId
    private Pk pk;

    @MapsId("taskId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @MapsId("milestoneId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;


    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    @Setter
    public static class Pk implements Serializable {
        @Column(name = "task_id")
        private Integer taskId;
        @Column(name = "milestone_id")
        private Integer milestoneId;
    }
}
