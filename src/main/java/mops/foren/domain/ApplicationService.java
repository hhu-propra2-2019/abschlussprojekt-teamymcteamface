package mops.foren.domain;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
public @interface ApplicationService {

    /**
     * Allows the user to directly specify the value of the annotation ("...")
     * without using (value = "...") syntax, if a value is needed
     * (example: ambiguity between beans).
     *
     * @return the specified value or "" if not specified
     */
    String value() default "";
}
