/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.tools.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import africa.dotpay.ftservice.tools.customAnnotations.ValidTransactionAmount;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public class TransactionAmountValidator implements ConstraintValidator<ValidTransactionAmount, Double> {

	@Override
	public boolean isValid(Double value, ConstraintValidatorContext context) {
		Boolean isValidVal = false;
		try {
			if (value != null && value > 0) {
				Double.valueOf(value);
				isValidVal = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidVal;
	}
}
