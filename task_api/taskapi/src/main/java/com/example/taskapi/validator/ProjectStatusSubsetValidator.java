package com.example.taskapi.validator;

import com.example.taskapi.annotation.ProjectStatusSubset;
import com.example.taskapi.entity.Project;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ProjectStatusSubsetValidator implements ConstraintValidator<ProjectStatusSubset, Project.Status> {
    private Project.Status[] subset;

    @Override
    public void initialize(ProjectStatusSubset constraintAnnotation) {
        this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(Project.Status value, ConstraintValidatorContext context) {
        return Arrays.asList(subset).contains(value);
    }
}
