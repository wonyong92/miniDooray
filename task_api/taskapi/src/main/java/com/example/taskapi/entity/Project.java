package com.example.taskapi.entity;


import com.example.taskapi.converter.StatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "Projects")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Project {

    @Id
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = StatusConverter.class)
    @Column(name = "status")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member member;

    @Getter
    public enum Status{
        ACTIVATE("A","활성"),
        DORMANCY("D","휴면"),
        TERMINATION("T", "종료");
        private String code;
        private String value;
        Status(String code, String value) {
            this.code = code;
            this.value = value;
        }
    }

}

