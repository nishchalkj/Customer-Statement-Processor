package com.cognizant.assignment.service;

import java.util.List;

import com.cognizant.assignment.exception.CustomerStatementProcessorException;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.CustomerStatementProcessorResponse;

/**
 * This is the interface for the service layer implementation classes
 * 
 * @author Nishchal
 *
 */
public interface CustomerStatementProcessorService {

	/**
	 * This method validates the list of customer statements and returns the
	 * response
	 * 
	 * @param request
	 *            contains list of customer statements
	 * @return validated response
	 * @throws CustomerStatementProcessorException
	 *             custom Exception
	 */
	CustomerStatementProcessorResponse processStatement(List<CustomerStatement> request)
			throws CustomerStatementProcessorException;

}
