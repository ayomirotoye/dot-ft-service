/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import africa.dotpay.ftservice.constants.MessageContent;
import africa.dotpay.ftservice.enums.TransactionStatusEnum;
import africa.dotpay.ftservice.models.ApiResponse;
import africa.dotpay.ftservice.models.TransactionRequest;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionControllerTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void whenValidRequest_ThenReturnOkHeader() throws Exception {
		TransactionRequest transactionRequest = TransactionRequest.builder().beneficiaryAccount("0014287628")
				.beneficiaryAccountName("Ayomide Akinrotoye").sourceAccount("0162302034")
				.sourceAccountName("Ayomide Akinrotoye").nameEnquirySessionId("188883883333")
				.status(TransactionStatusEnum.SUCCESSFUL.name()).amount(1000.00).build();
		ResponseEntity<ApiResponse> postResponse = restTemplate.postForEntity("/transaction/create", transactionRequest,
				ApiResponse.class);

		assertEquals(postResponse.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void whenTransactionStatusNotSupported_ThenReturnBadRequestHeader() throws Exception {
		TransactionRequest transactionRequest = TransactionRequest.builder().beneficiaryAccount("0014287628")
				.beneficiaryAccountName("Ayomide Akinrotoye").sourceAccount("0162302034")
				.sourceAccountName("Ayomide Akinrotoye").nameEnquirySessionId("188883883333").status("PENDING_PROGRESS")
				.amount(1000.00).build();
		ResponseEntity<ApiResponse> postResponse = restTemplate.postForEntity("/transaction/create", transactionRequest,
				ApiResponse.class);

		assertEquals(postResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(postResponse.getBody().getMessage().contains("Unsupported"));
	}

	@Test
	public void whenRequestParamNotSupported_ThenReturnBadRequest() throws Exception {
		TransactionRequest transactionRequest = TransactionRequest.builder().build();
		ResponseEntity<ApiResponse> postResponse = restTemplate
				.postForEntity("/transaction/fetch?transactionStatu=SUCCESSFUL", transactionRequest, ApiResponse.class);

		assertEquals(MessageContent.paramNotSpported, postResponse.getBody().getMessage());
	}

	@Test
	public void whenNoRecord_ThenReturnEmptyDataList() throws Exception {
		TransactionRequest transactionRequest = TransactionRequest.builder().build();
		ResponseEntity<ApiResponse> postResponse = restTemplate.postForEntity(
				"/transaction/fetch?transactionStatus=SUCCESSFUL", transactionRequest, ApiResponse.class);

		assertInstanceOf(List.class, postResponse.getBody().getData());
	}

}
