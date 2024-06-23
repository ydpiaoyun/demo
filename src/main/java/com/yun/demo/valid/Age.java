package com.yun.demo.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AgeValidator.class})
public @interface Age {
    String message() default "年龄不在合法范围内";
    int minAge() default 0;
    int maxAge() default 150;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}