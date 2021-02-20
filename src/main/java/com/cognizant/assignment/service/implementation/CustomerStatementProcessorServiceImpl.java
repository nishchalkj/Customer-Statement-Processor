package com.cognizant.assignment.service.implementation;

import static com.cognizant.assignment.constants.ApplicationConstants.DUPLICATE_REFERENCE;
import static com.cognizant.assignment.constants.ApplicationConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE;
import static com.cognizant.assignment.constants.ApplicationConstants.INCORRECT_END_BALANCE;
import static com.cognizant.assignment.constants.ApplicationConstants.REQUEST_EMPTY;
import static com.cognizant.assignment.constants.ApplicationConstants.SUCCESSFUL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cognizant.assignment.controller.CustomerStatementProcessorController;
import com.cognizant.assignment.exception.CustomerStatementProcessorException;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.CustomerStatementProcessorResponse;
import com.cognizant.assignment.model.ErrorRecord;
import com.cognizant.assignment.service.CustomerStatementProcessorService;
import com.cognizant.assignment.utils.StatementProcessorUtils;

/**
 * This is the service layer implementation of customer statement processor API
 * 
 * @author Nishchal
 *
 */
@Service
public class CustomerStatementProcessorServiceImpl implements CustomerStatementProcessorService {

	@Autowired
	private StatementProcessorUtils statementProcessor;

	private static final Logger log = LoggerFactory.getLogger(CustomerStatementProcessorController.class);

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
	@Override
	public CustomerStatementProcessorResponse processStatement(List<CustomerStatement> request)
			throws CustomerStatementProcessorException {

		log.debug("Validating customer statement");
		statementProcessor.validateInputStatements(request);
		final Set<CustomerStatement> duplicateRecords = statementProcessor.getDuplicates(request);
		final Set<CustomerStatement> invalidBalanceRecords = statementProcessor.getInvalidBalanceRecords(request);
		if (CollectionUtils.isEmpty(duplicateRecords) && CollectionUtils.isEmpty(invalidBalanceRecords)) {
			log.debug("Statement is valid");
			final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
			response.setResult(SUCCESSFUL);
			response.setErrorRecords(Collections.emptyList());
			return response;
		} else if (!CollectionUtils.isEmpty(duplicateRecords) && !CollectionUtils.isEmpty(invalidBalanceRecords)) {
			log.debug("Found {} duplicate records and {} invalid end balance records", duplicateRecords.size(),
					invalidBalanceRecords.size());
			final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
			final List<ErrorRecord> errorRecords = createErrorRecords(duplicateRecords);
			errorRecords.addAll(createErrorRecords(invalidBalanceRecords));
			response.setResult(DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
			response.setErrorRecords(errorRecords);
			return response;
		} else if (!CollectionUtils.isEmpty(duplicateRecords)) {
			log.debug("Found {} duplicate records", duplicateRecords.size());
			final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
			final List<ErrorRecord> errorRecords = createErrorRecords(duplicateRecords);
			response.setResult(DUPLICATE_REFERENCE);
			response.setErrorRecords(errorRecords);
			return response;
		} else {
			log.debug("Found {} invalid end balance records", invalidBalanceRecords.size());
			final CustomerStatementProcessorResponse response = new CustomerStatementProcessorResponse();
			final List<ErrorRecord> errorRecords = createErrorRecords(invalidBalanceRecords);
			response.setResult(INCORRECT_END_BALANCE);
			response.setErrorRecords(errorRecords);
			return response;
		}
	}

	/**
	 * This method set the required value to error record from customer
	 * statement
	 * 
	 * @param customerStatements
	 *            Contains list of validated customer statements
	 * @return list of error records
	 */
	private List<ErrorRecord> createErrorRecords(final Set<CustomerStatement> customerStatements) {
		final List<ErrorRecord> errorRecords = new ArrayList<>();
		for (final CustomerStatement statement : customerStatements) {
			final ErrorRecord record = new ErrorRecord();
			record.setAccountNumber(statement.getAccountNumber());
			record.setReference(statement.getTransactionReference());
			errorRecords.add(record);
		}
		return errorRecords;
	}

}
