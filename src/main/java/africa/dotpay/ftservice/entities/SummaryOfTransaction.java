/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummaryOfTransaction extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private String id;
	@Builder.Default
	Integer successfulTransactions = 0;
	@Builder.Default
	Integer failedTransactions = 0;
	@Builder.Default
	Integer totalTransactions = 0;
	@Builder.Default
	Integer processedTransactions = 0;
	@Builder.Default
	Integer isCommissionWorthyTransactions = 0;

}
