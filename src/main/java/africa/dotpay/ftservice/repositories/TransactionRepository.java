/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import africa.dotpay.ftservice.entities.Transaction;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
