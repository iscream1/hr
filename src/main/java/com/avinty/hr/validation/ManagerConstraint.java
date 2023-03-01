package com.avinty.hr.validation;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ManagerValidator.class)
@Target({ METHOD, CONSTRUCTOR })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManagerConstraint {
    Class<? extends Payload>[] payload() default {};
    String message() default "You can only set an employee as a manager who has a position of MANAGER.";
    Class<?>[] groups() default {};
}
