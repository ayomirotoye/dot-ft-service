/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.services;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import africa.dotpay.ftservice.entities.Transaction;
import africa.dotpay.ftservice.enums.FetchByEnum;
import africa.dotpay.ftservice.models.ApiResponse;
import africa.dotpay.ftservice.models.FetchRequest;
import africa.dotpay.ftservice.models.TransactionRequest;
import africa.dotpay.ftservice.models.TransactionSummary;
import africa.dotpay.ftservice.repositories.TransactionRepository;
import africa.dotpay.ftservice.tools.HelperUtils;
import africa.dotpay.ftservice.tools.ResponseUtils;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Service
public class TransactionService {

	private TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public ApiResponse saveTransaction(TransactionRequest transactionRequest) {
		Transaction transaction = Transaction.builder().beneficiaryAccount(transactionRequest.getBeneficiaryAccount())
				.beneficiaryAccountName(transactionRequest.getBeneficiaryAccountName())
				.sourceAccount(transactionRequest.getSourceAccount())
				.sourceAccountName(transactionRequest.getSourceAccountName())
				.nameEnquirySessionId(transactionRequest.getNameEnquirySessionId()).isProcessed(Boolean.FALSE)
				.status(transactionRequest.getStatus()).amount(transactionRequest.getAmount()).build();

		transactionRepository.save(transaction);

		return ResponseUtils.generateSuccessfulResponse(transaction);
	}

	public ApiResponse fetchListOfTransactions(FetchRequest fetchRequest, Pageable paging) throws ParseException {
		List<Transaction> list = Collections.emptyList();
		FetchByEnum fetchBy = FetchByEnum.valueOf(fetchRequest.getFetchBy());
		switch (fetchBy) {
		case TRANSACTION_STATUS:
			list = transactionRepository.findByStatus(fetchRequest.getFetchFor(), paging);
			break;
		case SOURCE_ACCOUNT:
			list = transactionRepository.findBySourceAccount(fetchRequest.getFetchFor(), paging);
			break;
		case BENEFICIARY_ACCOUNT:
			list = transactionRepository.findByBeneficiaryAccount(fetchRequest.getFetchFor(), paging);
			break;
		case TRANSACTION_DATE:
			Date searchDate = HelperUtils.convertStringToDate(fetchRequest.getFetchFor());
			list = transactionRepository.findByCreatedOn(searchDate, paging);
			break;
		case TRANSACTION_SUMMARY:
			searchDate = HelperUtils.convertStringToDate(fetchRequest.getFetchFor());
			list = transactionRepository.findByCreatedOnLessThanEqual(searchDate);
			TransactionSummary summary = new TransactionSummary();

			summary = HelperUtils.summarize(list, summary);
			return ResponseUtils.generateSuccessfulResponse(summary);
		default:
			break;
		}

		return ResponseUtils.generateSuccessfulResponse(list);
	}

}
