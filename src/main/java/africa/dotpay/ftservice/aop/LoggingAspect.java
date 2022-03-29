package africa.dotpay.ftservice.aop;

/**
 * @author Ayomide.Akinrotoye
 *
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import africa.dotpay.ftservice.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

	public final Environment env;
	public final ObjectMapper objectMapper;

	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void serviceClasses() {
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restControllerClasses() {
	}

	@Pointcut("execution(* *.*(..))")
	protected void allMethod() {
	}

	@Pointcut("within(@africa.dotpay.ftservice.aop.AdviceLogs *)")
	protected void withinAnAdvice() {
	}

	@Pointcut("execution(public * *(..))")
	protected void allPublicMethods() {
	}

	@AfterReturning(pointcut = "(restControllerClasses() && allMethod())", returning = "result")
	public void logAfter(JoinPoint joinPoint, Object result) {
		String returnValue = this.getValue(result);
		log.info("RESPONSE TO CLIENT : " + returnValue);
	}

	@Around("withinAnAdvice() & allPublicMethods()")
	public Object printError(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = proceedingJoinPoint.proceed();
		log.debug("==================== ERROR OCCURRED =================");
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		Object[] methodArgs = proceedingJoinPoint.getArgs();
		String[] methodParams = methodSignature.getParameterNames();
		printExceptionStackTrace(methodArgs, methodParams);
		return result;
	}

	@Around("serviceClasses()")
	public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();
		Object[] methodArgs = proceedingJoinPoint.getArgs();
		String[] methodParams = methodSignature.getParameterNames();

		log.info("IN => " + className + "." + methodName + " <=");

		if (Boolean.valueOf(env.getProperty("aop.logger.method.show_arguments.enabled", "true"))) {
			String reqPayload = printRequestPayload(methodArgs, methodParams);
			if (!reqPayload.equalsIgnoreCase("")) {

				log.info("REQUEST FROM CLIENT => " + reqPayload + " <=");
			}
		}

		Object result = proceedingJoinPoint.proceed();
		if (Boolean.valueOf(env.getProperty("aop.logger.exit.summary.enabled", "true"))) {
			StopWatch stopWatch = new StopWatch(className + "->" + methodName);
			stopWatch.start(methodName);
			stopWatch.stop();

			log.info("TOOK => (" + stopWatch.getTotalTimeSeconds() + " seconds) TO EXECUTE <=");
		}
		return result;
	}

	private String printRequestPayload(Object[] methodArgs, String[] methodParams) {
		String payloadLog = "";
		if (Objects.nonNull(methodParams) && Objects.nonNull(methodArgs) && methodParams.length == methodArgs.length) {
			Map<String, Object> values = new HashMap<>(methodParams.length);
			for (int i = 0; i < methodParams.length; i++) {
				values.put(methodParams[i], methodArgs[i]);
			}
			try {
				payloadLog = objectMapper.writeValueAsString(values);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

		return payloadLog;
	}

	private String printExceptionStackTrace(Object[] methodArgs, String[] methodParams) {
		String payloadLog = "";
		if (Objects.nonNull(methodParams) && Objects.nonNull(methodArgs) && methodParams.length == methodArgs.length) {
			for (int i = 0; i < methodParams.length; i++) {
				if (methodArgs[i].getClass().isAssignableFrom(Exception.class)) {
					Exception e = (Exception) methodArgs[i];
					log.info("EXCEPTION STACK TRACE => " + " <=");
					e.printStackTrace();
				} else if (methodArgs[i].getClass().isAssignableFrom(CustomException.class)) {
					CustomException e = (CustomException) methodArgs[i];
					log.info("EXCEPTION STACK TRACE => " + " <=");
					e.printStackTrace();
				} else {
					log.info("EXCEPTION BODY => " + methodArgs[i] + " <=");
				}
			}

		}

		return payloadLog;
	}

	private String getValue(Object result) {
		String returnValue = null;
		if (null != result) {
			if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
				returnValue = ReflectionToStringBuilder.toString(result);
			} else {
				returnValue = result.toString();
			}
		}
		return returnValue;
	}

}
