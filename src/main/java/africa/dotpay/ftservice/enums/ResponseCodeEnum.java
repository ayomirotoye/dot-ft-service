/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public enum ResponseCodeEnum {
	SUCCESS("00", "Successful"), FAILED("01", "Failed"), BAD_REQUEST("400", "Bad request");

	private ResponseCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Getter
	@Setter
	private String code;
	@Getter
	@Setter
	private String message;
}
