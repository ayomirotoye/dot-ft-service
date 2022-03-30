/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.tools.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import africa.dotpay.ftservice.tools.HelperUtils;
import africa.dotpay.ftservice.tools.customAnnotations.ValidTransactionFilterDate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Slf4j
public class TransactionFilterDateValidator implements ConstraintValidator<ValidTransactionFilterDate, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Boolean isValidVal = false;
		try {
			if (value != null) {
				HelperUtils.convertStringToDate(value);
				isValidVal = true;
			} else {
				isValidVal = true;
			}
		} catch (Exception e) {
			log.info("THERE IS AN ISSUE WITH DATE PROVIDED");
		}
		return isValidVal;
	}
}
