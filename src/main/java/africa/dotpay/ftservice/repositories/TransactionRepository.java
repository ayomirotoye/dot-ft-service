/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import africa.dotpay.ftservice.entities.Transaction;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {
	List<Transaction> findByStatus(String status, Pageable paging);

	List<Transaction> findByTransactionReference(String transactionReference);

	List<Transaction> findBySourceAccount(String sourceAccount, Pageable paging);

	List<Transaction> findByBeneficiaryAccount(String beneficiaryAccount, Pageable paging);

	List<Transaction> findByCreatedOnLessThanEqual(Date theDate);

	List<Transaction> findByCreatedOn(Date theDate);

	List<Transaction> findByIsProcessedTrue();

	List<Transaction> findByIsProcessedFalse();
}
