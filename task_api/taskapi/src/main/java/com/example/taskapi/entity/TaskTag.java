package com.example.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "TaskTags")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskTag {
    @EmbeddedId
    private Pk pk;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @MapsId("taskId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    @Getter
    @Setter
    public static class Pk implements Serializable {

        @Column(name = "task_id")
        private Integer taskId;
        @Column(name = "tag_id")
        private Integer tagId;
    }


}
