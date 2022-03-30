/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.models;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import africa.dotpay.ftservice.tools.customAnnotations.ValidTransactionAmount;
import africa.dotpay.ftservice.tools.customAnnotations.ValidTransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
	private String beneficiaryAccountName;
	@NotBlank
	private String sourceAccount;
	private String sourceAccountName;
	@Length(min = 8, max = 32)
	private String transactionReference;
	private String beneficiaryAccount;
	@Length(min = 8, max = 32)
	private String nameEnquirySessionId;
	@ValidTransactionStatus
	private String status;
	@ValidTransactionAmount
	private Double amount;
}
