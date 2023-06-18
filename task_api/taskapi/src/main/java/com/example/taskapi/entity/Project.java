package com.example.taskapi.entity;


import com.example.taskapi.converter.StatusConverter;
import com.example.taskapi.domain.ProjectUpdateRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.stream.Stream;

@Table(name = "Projects")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = StatusConverter.class)
    @Column(name = "status")
    private Status status;

    public void deleteProject() {
        this.status = Status.TERMINATION;
    }
    @Getter
    public enum Status {
        ACTIVATE("A", "활성"),
        DORMANCY("D", "휴면"),
        TERMINATION("T", "종료"),
        ;
        private String code;
        private String value;

        Status(String code, String value) {
            this.code = code;
            this.value = value;
        }

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public static Status findByName(String name) {
            return Stream.of(Status.values())
                    .filter(s -> s.name().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }

    public Project(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public void updateProjectWithDto(ProjectUpdateRequest projectUpdateRequest) {
        this.name = projectUpdateRequest.getName();
        this.status = projectUpdateRequest.getStatus();
    }
}

