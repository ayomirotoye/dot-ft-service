/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.models;

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
public class TransactionSummary {
	@Builder.Default
	Integer successfulTransactions = 0;
	@Builder.Default
	Integer failedTransactions = 0;
	@Builder.Default
	Integer totalTransactions = 0;
	@Builder.Default
	Integer processedTransactions = 0;
	@Builder.Default
	Integer isCommissionWorthyTransactions = 0;

	public void addToProcessed(Integer count) {
		this.processedTransactions += count;
	}

	public void addToFailed(Integer count) {
		this.failedTransactions += count;
	}

	public void addToSuccessful(Integer count) {
		this.successfulTransactions += count;
	}

	public void addToCommissionWorthy(Integer count) {
		this.isCommissionWorthyTransactions += count;
	}
}
