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

import africa.dotpay.ftservice.tools.validators.TransactionAmountValidator;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Documented
@Constraint(validatedBy = TransactionAmountValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionAmount {
	String message() default "Transaction amount must be greater than 0 ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
