package com.yun.demo.aop;


import com.yun.demo.annotation.EnumString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangxiaoyun27
 * @date 2023/2/1 14:43
 */
public class EnumStringValidator implements ConstraintValidator<EnumString, String> {

    private List<String> enumStringList;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        enumStringList = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(Objects.isNull(value)) {
            return true;
        }
        return enumStringList.contains(value);
    }
}
