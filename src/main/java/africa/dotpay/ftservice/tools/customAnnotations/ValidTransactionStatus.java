/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.tools.customAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import africa.dotpay.ftservice.tools.validators.StatusValidator;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Documented
@Constraint(validatedBy = StatusValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionStatus {
	String message() default "Unsupported transaction status";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
