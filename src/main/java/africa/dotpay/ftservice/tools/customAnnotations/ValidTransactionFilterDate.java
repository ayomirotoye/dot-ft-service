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

import africa.dotpay.ftservice.tools.validators.TransactionFilterDateValidator;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Documented
@Constraint(validatedBy = TransactionFilterDateValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTransactionFilterDate {
	String message() default "Date must be in this format: yyyy-MM-dd hh:mm:ss.SS ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
