package vn.edu.vnua.qlsvfita.validator.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = InputDateValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface InputDate {
    String message() default "Default message date not valid";

    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}