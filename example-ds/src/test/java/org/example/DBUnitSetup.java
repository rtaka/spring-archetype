package org.example;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DBUnitSetup {
    String init() default "";

    String expected() default "";

    boolean cleanup() default false;
}
