/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 29, 2022
 */
package africa.dotpay.ftservice.tools;

import africa.dotpay.ftservice.enums.ResponseCodeEnum;
import africa.dotpay.ftservice.models.ApiResponse;

/**
 * @author Ayomide.Akinrotoye
 *
 */
public class ResponseUtils {
	public static ApiResponse generateResponse(String responseCode, boolean hasError, String message, Object data) {
		return ApiResponse.builder().responseCode(responseCode).hasError(hasError).message(message).data(data).build();
	}

	public static ApiResponse generateSuccessfulResponse(String message) {
		return ApiResponse.builder().responseCode(ResponseCodeEnum.SUCCESS.getCode()).hasError(false).message(message)
				.build();
	}
	
	public static ApiResponse generateSuccessfulResponse(Object data) {
		return ApiResponse.builder().responseCode(ResponseCodeEnum.SUCCESS.getCode()).message(ResponseCodeEnum.SUCCESS.getMessage()).hasError(false).data(data)
				.build();
	}
	
	public static ApiResponse generateBadResponse(String message) {
		return ApiResponse.builder().responseCode(ResponseCodeEnum.BAD_REQUEST.getCode()).hasError(false).message(message)
				.build();
	}

}
