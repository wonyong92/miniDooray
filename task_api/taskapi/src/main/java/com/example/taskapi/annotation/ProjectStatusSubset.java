package com.example.taskapi.annotation;

import com.example.taskapi.entity.Project;
import com.example.taskapi.validator.ProjectStatusSubsetValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ProjectStatusSubsetValidator.class)
public @interface ProjectStatusSubset {
    Project.Status[] anyOf();
    String message() default "must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
