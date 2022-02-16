package com.ares.log.common;

import java.lang.annotation.*;

/**
 * @description:
 * @author: Young 2020/01/27
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String value() default "";

    boolean isIgnore() default false;
}
