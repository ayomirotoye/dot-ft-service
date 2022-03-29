/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
		TransactionRequest transactionRequest = TransactionRequest.builder().build();
		ResponseEntity<ApiResponse> postResponse = restTemplate.postForEntity("/transaction/create", transactionRequest,
				ApiResponse.class);

		assertEquals(postResponse.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void whenNoRecord_ThenReturnEmptyDataList() throws Exception {
		TransactionRequest transactionRequest = TransactionRequest.builder().build();
		ResponseEntity<ApiResponse> postResponse = restTemplate.postForEntity("/transaction/fetch", transactionRequest,
				ApiResponse.class);

		assertEquals(postResponse.getBody(), HttpStatus.OK);
	}

}
