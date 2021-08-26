package org.example.ds;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@ExtendWith(DBUnitExtension.class)
@ExtendWith(MapperInjectExtension.class)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DBUnitTest {
}
