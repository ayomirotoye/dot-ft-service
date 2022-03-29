/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.services;

import org.springframework.stereotype.Service;

import africa.dotpay.ftservice.entities.Transaction;
import africa.dotpay.ftservice.models.ApiResponse;
import africa.dotpay.ftservice.models.TransactionRequest;
import africa.dotpay.ftservice.repositories.TransactionRepository;
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
				.nameEnquirySessionId(transactionRequest.getNameEnquirySessionId())
				.status(transactionRequest.getStatus()).amount(transactionRequest.getAmount()).build();

		transactionRepository.save(transaction);

		return ResponseUtils.generateSuccessfulResponse(transaction);
	}

}
