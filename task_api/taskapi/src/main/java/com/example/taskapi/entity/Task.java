package com.example.taskapi.entity;

import com.example.taskapi.domain.TaskUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@ToString
@Table(name = "Tasks")
public class Task {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @Column(name ="title", nullable = false)
    private String title;
    
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    public Task(String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, Member member, Project project) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.member = member;
        this.project = project;
    }

    public void updateTaskWithDto(TaskUpdateRequest taskUpdateRequest) {
        this.title = taskUpdateRequest.getTitle();
        this.content = taskUpdateRequest.getContent();
        this.modifiedAt = LocalDateTime.now();
    }
}
