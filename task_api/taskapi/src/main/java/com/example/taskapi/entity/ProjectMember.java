package com.example.taskapi.entity;

import com.example.taskapi.converter.RoleConverter;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "ProjectMembers")
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    @Column(name = "role")
    @Convert(converter = RoleConverter.class)
    private Role role;

    public enum Role {
        ADMIN("관리자"), MEMBER("사용자");
        Role(String value) {
            this.value = value;
        }

        private String value;
    }

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
