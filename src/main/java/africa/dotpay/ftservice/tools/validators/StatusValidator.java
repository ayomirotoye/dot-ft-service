/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.tools.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import africa.dotpay.ftservice.enums.TransactionStatusEnum;
import africa.dotpay.ftservice.tools.customAnnotations.ValidTransactionStatus;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public class StatusValidator implements ConstraintValidator<ValidTransactionStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Boolean isValidVal = false;
		try {
			if (value != null) {
				TransactionStatusEnum.valueOf(value);
				isValidVal = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidVal;
	}
}
