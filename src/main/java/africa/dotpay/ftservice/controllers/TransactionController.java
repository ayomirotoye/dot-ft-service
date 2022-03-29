/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.controllers;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import africa.dotpay.ftservice.models.ApiResponse;
import africa.dotpay.ftservice.models.TransactionRequest;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@RestController
@RequestMapping("transaction")
public class TransactionController {

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> postTransaction(@RequestBody TransactionRequest transactionRequest) {
		return null;
	}

	@PostMapping("/fetch")
	public ResponseEntity<ApiResponse> fetchTransactions(
			@RequestBody(required = false) HashMap<String, Object> dateRange,
			@RequestParam(required = false) String transactionStatus,
			@RequestParam(required = false) String transactionDate,
			@RequestParam(required = false) String sourceAccount,
			@RequestParam(required = false) String beneficiaryAccount, @RequestParam(required = false) String userId) {
		return null;
	}
}
