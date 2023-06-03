package com.example.taskapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "ProjectMembers")
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMember {

    @EmbeddedId
    private Pk pk;

    @MapsId("memberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @MapsId("projectId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;


    @Embeddable
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Pk implements Serializable {
        @Column(name = "member_id")
        private String memberId;
        @Column(name = "project_id")
        private Integer projectId;
    }
}
