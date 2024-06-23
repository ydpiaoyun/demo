package com.yun.demo.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<Age, Integer> {
    
    private int minAge;
    private int maxAge;

    @Override
    public void initialize(Age constraintAnnotation) {
        this.minAge = constraintAnnotation.minAge();
        this.maxAge = constraintAnnotation.maxAge();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null) {
            return true; // 允许为空值
        }
        if(value < minAge || value > maxAge) {
            return false; // 验证不通过
        }
        return true; // 验证通过
    }
}