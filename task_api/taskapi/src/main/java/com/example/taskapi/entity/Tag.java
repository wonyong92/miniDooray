package com.example.taskapi.entity;

import com.example.taskapi.domain.TagUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "Tags")
@Entity
@NoArgsConstructor
@Getter
@ToString
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer tagId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public Tag(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    public void updateTagWithDto(TagUpdateRequest tagUpdateRequest) {
        this.name = tagUpdateRequest.getName();
    }
}
