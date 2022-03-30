/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.controllers;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import africa.dotpay.ftservice.enums.FetchByEnum;
import africa.dotpay.ftservice.models.ApiResponse;
import africa.dotpay.ftservice.models.FetchRequest;
import africa.dotpay.ftservice.models.TransactionRequest;
import africa.dotpay.ftservice.services.TransactionService;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@RestController
@RequestMapping("transaction")
@Validated
public class TransactionController {
	private TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> postTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
		ApiResponse apiResponse = transactionService.saveTransaction(transactionRequest);
		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/fetch")
	public ResponseEntity<ApiResponse> fetchTransactions(
			@RequestBody(required = false) HashMap<String, Object> dateRange,
			@RequestParam(required = false) String transactionStatus,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(required = false) String transactionDate,
			@RequestParam(required = false) String sourceAccount,
			@RequestParam(required = false) String beneficiaryAccount, @RequestParam(required = false) String userId) {

		FetchRequest fetchRequest = null;

		if (transactionStatus != null) {
			fetchRequest = FetchRequest.builder().fetchBy(FetchByEnum.TRANSACTION_STATUS.name())
					.fetchFor(transactionStatus).build();
		} else if (transactionDate != null) {
			fetchRequest = FetchRequest.builder().fetchBy(FetchByEnum.TRANSACTION_DATE.name()).fetchFor(transactionDate)
					.build();
		} else if (sourceAccount != null) {
			fetchRequest = FetchRequest.builder().fetchBy(FetchByEnum.SOURCE_ACCOUNT.name()).fetchFor(sourceAccount)
					.build();
		} else if (beneficiaryAccount != null) {
			fetchRequest = FetchRequest.builder().fetchBy(FetchByEnum.BENEFICIARY_ACCOUNT.name())
					.fetchFor(beneficiaryAccount).build();
		}

		if (dateRange != null) {
			fetchRequest.setDateRange(dateRange);
		}

		Pageable paging = PageRequest.of(pageNo, pageSize);
		ApiResponse apiResponse = transactionService.fetchListOfTransactions(fetchRequest, paging);

		return ResponseEntity.ok(apiResponse);
	}
}
