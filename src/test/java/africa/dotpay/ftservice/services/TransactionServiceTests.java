/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import africa.dotpay.ftservice.entities.Transaction;
import africa.dotpay.ftservice.enums.FetchByEnum;
import africa.dotpay.ftservice.models.FetchRequest;
import africa.dotpay.ftservice.models.TransactionRequest;
import africa.dotpay.ftservice.repositories.TransactionRepository;

/**
 * @author Ayomide.Akinrotoye
 *
 */

@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class TransactionServiceTests {

	@InjectMocks
	private TransactionService transactionService;

	@Mock
	private TransactionRepository transactionRepository;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void whenSaveTransaction_thenPersistNewTransaction() {
		TransactionRequest transactionRequest = TransactionRequest.builder().beneficiaryAccount("0014287628")
				.beneficiaryAccountName("Ayomide Akinrotoye").sourceAccount("0162302034")
				.sourceAccountName("Ayomide Akinrotoye").nameEnquirySessionId("188883883333").status("PENDING_PROGRESS")
				.amount(1000.00).build();

		transactionService.saveTransaction(transactionRequest);

		Transaction transaction = Transaction.builder().beneficiaryAccount(transactionRequest.getBeneficiaryAccount())
				.beneficiaryAccountName(transactionRequest.getBeneficiaryAccountName())
				.sourceAccount(transactionRequest.getSourceAccount())
				.sourceAccountName(transactionRequest.getSourceAccountName())
				.nameEnquirySessionId(transactionRequest.getNameEnquirySessionId()).isProcessed(Boolean.FALSE)
				.status(transactionRequest.getStatus()).amount(transactionRequest.getAmount()).build();

		verify(transactionRepository, times(1)).save(transaction);
	}
	
	@Test
	public void whenFetchTransaction_thenFindMatchingTransaction() throws ParseException {
		FetchRequest fetchRequest = FetchRequest.builder()
				.fetchBy(FetchByEnum.TRANSACTION_STATUS.name())
				.fetchFor("SUCCESSFUL")
				.build();
		transactionService.fetchListOfTransactions(fetchRequest, null);

		verify(transactionRepository, times(1)).findByStatus(fetchRequest.getFetchFor(), null);
	}

}
