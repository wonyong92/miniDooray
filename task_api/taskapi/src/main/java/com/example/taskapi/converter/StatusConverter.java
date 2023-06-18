package com.example.taskapi.converter;

import com.example.taskapi.entity.Project;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

public class StatusConverter implements AttributeConverter<Project.Status, String> {
    @Override
    public String convertToDatabaseColumn(Project.Status attribute) {
        return attribute.getCode();
    }

    @Override
    public Project.Status convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(Project.Status.class)
                .stream()
                .filter(status -> status.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Invalid dbData"));
    }
}
