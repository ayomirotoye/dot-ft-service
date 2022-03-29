package africa.dotpay.ftservice.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -1335544445896736296L;

	private String message;

	public CustomException(String message) {
		this.message = message;
	}

}