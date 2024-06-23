package com.yun.demo.annotation;

import com.yun.demo.aop.EnumStringValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ClassName: EnumString <br/>
 * Description: <br/>
 * date: 2023/4/26 18:52<br/>
 *
 * @author zxy<br />
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumStringValidator.class)//标明由哪个类执行校验逻辑
public @interface EnumString {
    String message() default "value not in enum values.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return date must in this value array
     */
    String[] value();
}

