package com.cognizant.assignment.exception;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cognizant.assignment.model.CustomerStatementProcessorResponse;


/**
 * Global exception handler for the application
 * @author Nishchal
 *
 */
@ControllerAdvice
public class CustomerStatementProcessorExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * This method handles the custom exception thrown in the application
	 * @param exception CustomerStatementProcessorException- exception to be thrown
	 * @return ResponseEntity<CustomerStatementProcessorResponse>
	 */
	@ExceptionHandler(CustomerStatementProcessorException.class)
	public ResponseEntity<CustomerStatementProcessorResponse> handleCustomException(final CustomerStatementProcessorException exception) {
        final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
        response.setResult(exception.getMessage());
        response.setErrorRecords(exception.getErrorRecords());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
	
	
	/**
	 * This method handles the generic exception thrown in the application
	 * @param exception Exception - exception to be thrown
	 * @return ResponseEntity<CustomerStatementProcessorResponse>
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<CustomerStatementProcessorResponse> handleException(final Exception exception) {
        final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
        response.setResult("INTERNAL_SERVER_ERROR");
        response.setErrorRecords(Collections.emptyList());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

	
	

}
