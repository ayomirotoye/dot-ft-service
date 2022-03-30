/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Transaction extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", updatable = false, nullable = false)
	private String id;
	private String beneficiaryAccountName;
	private String sourceAccount;
	private String sourceAccountName;
	private String transactionReference;
	private String beneficiaryAccount;
	private String nameEnquirySessionId;
	private String status;
	private Double amount;
	private Boolean isCommissionWorthy;
	private Double commissionAmount;
	private Double feeAmount;
	private Boolean isProcessed;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date transactionProcessTime;
	@Version
	Long version;

}
