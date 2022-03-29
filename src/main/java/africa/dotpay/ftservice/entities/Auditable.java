/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Feb 4, 2022
 */
package africa.dotpay.ftservice.entities;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author Ayomide.Akinrotoye
 *
 */
@Data
@MappedSuperclass
public abstract class Auditable<U> {

	@CreatedBy
	protected U createdBy;

	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@CreationTimestamp
	protected Date createdOn;

	@LastModifiedBy
	protected U lastModifiedBy;

	@UpdateTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	protected Date updatedOn;

}
