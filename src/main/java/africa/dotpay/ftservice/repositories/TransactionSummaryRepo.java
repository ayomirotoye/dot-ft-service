/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import africa.dotpay.ftservice.entities.SummaryOfTransaction;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public interface TransactionSummaryRepo extends JpaRepository<SummaryOfTransaction, String> {

}
