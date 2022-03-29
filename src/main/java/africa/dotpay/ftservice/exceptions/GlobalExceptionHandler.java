package africa.dotpay.ftservice.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.ServiceUnavailableException;
import javax.validation.ConstraintDefinitionException;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.springframework.core.annotation.Order;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import africa.dotpay.ftservice.aop.AdviceLogs;
import africa.dotpay.ftservice.constants.MessageContent;
import africa.dotpay.ftservice.enums.ResponseCodeEnum;
import lombok.extern.slf4j.Slf4j;

@AdviceLogs
@Order(1000)
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private static HashMap<String, Object> errorBodyBuilder(Integer code, Boolean errorFlag, String message,
			Object data) {
		HashMap<String, Object> errorBody = new HashMap<>();

		errorBody.put("responseCode", code == null ? ResponseCodeEnum.FAILED.getCode() : code);
		errorBody.put("hasError", true);
		errorBody.put("message", message);
		errorBody.put("data", data);

		return errorBody;
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.OK)
	public HashMap<String, Object> handleNotFoundException(NotFoundException se) {
		return errorBodyBuilder(Integer.valueOf(ResponseCodeEnum.SUCCESS.getCode()), true, se.getMessage(), null);
	}

	@ExceptionHandler(org.springframework.web.bind.MissingRequestHeaderException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public HashMap<String, Object> handleMissingRequestHeaderException(
			org.springframework.web.bind.MissingRequestHeaderException se) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true, se.getMessage(), null);
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public HashMap<String, Object> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException se) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true, se.getMessage(), se.getErrorCode());
	}

	@ExceptionHandler(ConstraintDefinitionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public HashMap<String, Object> handleConstraintDefinitionException(ConstraintDefinitionException se) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true, se.getMessage(), se.getLocalizedMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public Errors validationExceptionHandler(MethodArgumentNotValidException ex) {
		Map<String, String> responseBody = new HashMap<>();

		Errors errors = new Errors(ResponseCodeEnum.BAD_REQUEST.getCode(), ResponseCodeEnum.BAD_REQUEST.getMessage());
		try {
			ex.getBindingResult().getAllErrors().forEach(error -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				responseBody.put(fieldName, errorMessage);
			});
			errors.setData(responseBody);
		} catch (Exception e) {
			if (ex.getBindingResult() != null) {
				errors = processFieldErrors(ex.getBindingResult().getAllErrors(), errors, responseBody);
			}
		}
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class })
	public Errors validationExceptionHandler(ConstraintViolationException ex) {
		Map<String, String> responseBody = new HashMap<>();

		Errors errors = new Errors(ResponseCodeEnum.BAD_REQUEST.getCode(), ResponseCodeEnum.BAD_REQUEST.getMessage());
		log.info(ex.toString());
		try {
			ex.getConstraintViolations().forEach(error -> {
				String fieldName = error.getPropertyPath() != null ? String.valueOf(error.getPropertyPath()) : "";
				String errorMessage = error.getMessage();
				responseBody.put(fieldName, errorMessage);
			});
			errors.setData(responseBody);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return errors;
	}

	private Errors processFieldErrors(List<ObjectError> fieldErrors, Errors errors, Map<String, String> responseBody) {

		if (fieldErrors != null) {
			fieldErrors.stream().forEach(error -> {
				String fieldName = ((ObjectError) error).getCode();
				String errorMessage = error.getDefaultMessage();
				responseBody.put(fieldName, errorMessage);
			});
			errors.setData(responseBody != null ? responseBody.values() : ResponseCodeEnum.BAD_REQUEST.getMessage());
		}

		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public HashMap<String, Object> validateMissingServletRequestParameterException(
			MissingServletRequestParameterException ex) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true, ex.getParameterName().concat(" is missing"),
				null);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RuntimeException.class })
	public HashMap<String, Object> RunTimeException(RuntimeException ex) {
		return errorBodyBuilder(HttpStatus.INTERNAL_SERVER_ERROR.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ UnexpectedTypeException.class })
	public HashMap<String, Object> handleUnexpectedTypeException(UnexpectedTypeException ex) {
		return errorBodyBuilder(Integer.valueOf(ResponseCodeEnum.FAILED.getCode()), true,
				ResponseCodeEnum.FAILED.getMessage(), null);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ NullPointerException.class })
	public HashMap<String, Object> handleNullPointerException(NullPointerException ex) {
		return errorBodyBuilder(HttpStatus.INTERNAL_SERVER_ERROR.value(), true, MessageContent.nullPointer, null);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public Map<String, Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		return errorBodyBuilder(HttpStatus.METHOD_NOT_ALLOWED.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public Map<String, Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
		return errorBodyBuilder(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpClientErrorException.class })
	public Map<String, Object> handleHttpClientErrorException(HttpClientErrorException ex) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ ServiceUnavailableException.class })
	public Map<String, Object> handleServiceUnavailableException(ServiceUnavailableException ex) {
		return errorBodyBuilder(HttpStatus.SERVICE_UNAVAILABLE.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ ResourceAccessException.class })
	public Map<String, Object> handleResourceAccessException(ResourceAccessException ex) {
		return errorBodyBuilder(HttpStatus.SERVICE_UNAVAILABLE.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true,
				ResponseCodeEnum.BAD_REQUEST.getMessage().concat(" | Request body is missing ..."), null);
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler({ HttpServerErrorException.class })
	public Map<String, Object> handleHttpServerErrorException(HttpServerErrorException ex) {
		return errorBodyBuilder(HttpStatus.SERVICE_UNAVAILABLE.value(), true, null, null);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ CustomException.class })
	public Map<String, Object> handleCustomException(CustomException ex) {
		return errorBodyBuilder(HttpStatus.BAD_REQUEST.value(), true, ex.getMessage(), null);
	}

}