/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import africa.dotpay.ftservice.entities.Transaction;
import africa.dotpay.ftservice.enums.TransactionStatusEnum;
import africa.dotpay.ftservice.models.TransactionSummary;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public class HelperUtils {

	public static Date convertStringToDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
		Date convertedDate = simpleDateFormat.parse(date);

		return convertedDate;
	}

	private static TransactionSummary summarize(Transaction transaction, TransactionSummary summary) {
		if (transaction.getIsProcessed() != null && transaction.getIsProcessed()) {
			summary.addToProcessed(1);
		}

		if (transaction.getIsCommissionWorthy() != null && transaction.getIsCommissionWorthy()) {
			summary.addToCommissionWorthy(1);
		}

		if (transaction.getStatus().equalsIgnoreCase(TransactionStatusEnum.SUCCESSFUL.name())) {
			summary.addToSuccessful(1);
		} else {
			summary.addToFailed(1);
		}

		return summary;

	}

	public static TransactionSummary summarize(List<Transaction> transactions, TransactionSummary summary) {
		summary.setTotalTransactions(transactions.size());
		transactions.stream().forEach(tnx -> {
			summarize(tnx, summary);
		});
		return summary;
	}
}
