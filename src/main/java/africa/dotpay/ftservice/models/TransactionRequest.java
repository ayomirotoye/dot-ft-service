/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.models;

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
	private String sourceAccount;
	private String transactionReference;
	private String beneficiaryAccount;
	private String nameEnquirySessionId;
	@ValidTransactionStatus
	private String status;
	@ValidTransactionAmount
	private Double amount;
}
