package com.example.taskapi.converter;

import com.example.taskapi.entity.Project;
import com.example.taskapi.entity.ProjectMember;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class RoleConverter implements AttributeConverter<ProjectMember.Role, String> {
    @Override
    public String convertToDatabaseColumn(ProjectMember.Role attribute) {
        return attribute.toString();
    }

    @Override
    public ProjectMember.Role convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(ProjectMember.Role.class)
                .stream()
                .filter(status -> status.toString().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Invalid dbData"));
    }

}
