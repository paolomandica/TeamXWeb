package com.jpl.teamx.validator;

import com.jpl.teamx.model.Team;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TeamValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Team.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "Name cannot be empty!");
    }
}
