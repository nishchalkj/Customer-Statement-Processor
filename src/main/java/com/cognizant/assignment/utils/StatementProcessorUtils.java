package com.cognizant.assignment.utils;

import static com.cognizant.assignment.constants.ApplicationConstants.BAD_REQUEST;
import static com.cognizant.assignment.constants.ApplicationConstants.REQUEST_EMPTY;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cognizant.assignment.exception.CustomerStatementProcessorException;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.ErrorRecord;

/**
 * This is the utiltiy class for validation of customer statements
 * 
 * @author Nishchal
 *
 */
@Component
public class StatementProcessorUtils {

	private static final Logger log = LoggerFactory.getLogger(StatementProcessorUtils.class);

	
	public void validateInputStatements(final List<CustomerStatement> customerStatements) throws CustomerStatementProcessorException{
		if (customerStatements.isEmpty()) {
			final List<ErrorRecord> errorRecords = Collections.emptyList();
			throw new CustomerStatementProcessorException(REQUEST_EMPTY, errorRecords);
		}
	}
	/**
	 * This method finds and returns duplicate transactions from passed
	 * statement.
	 *
	 * @param list
	 *            of CustomerStatement
	 * @return set of duplicate CustomerStatement
	 * @throws CustomerStatementProcessorException
	 */
	public Set<CustomerStatement> getDuplicates(final List<CustomerStatement> customerStatements)
			throws CustomerStatementProcessorException {
		Set<CustomerStatement> duplicateStatements = null;
		try {
			duplicateStatements = customerStatements.stream()
					.collect(Collectors.groupingBy(CustomerStatement::getTransactionReference)).entrySet().stream()
					.filter(statement -> statement.getValue().size() > 1).flatMap(d -> d.getValue().stream())
					.collect(Collectors.toSet());
		} catch (RuntimeException ex) {
			log.error("Exception thrown while parsing the JSON request for Duplicate ref values", ex);
			final List<ErrorRecord> errorRecords = Collections.emptyList();
			throw new CustomerStatementProcessorException(BAD_REQUEST, errorRecords);
		}
		return duplicateStatements;
	}

	/**
	 * This method finds and returns invalid end balance records from passed
	 * statement.
	 *
	 * @param list
	 *            of CustomerStatement
	 * @return set of invalid balance CustomerStatement
	 * @throws CustomerStatementProcessorException
	 */
	public Set<CustomerStatement> getInvalidBalanceRecords(final List<CustomerStatement> customerStatements)
			throws CustomerStatementProcessorException {
		Set<CustomerStatement> invalidBalanceRecords = null;
		try {
			invalidBalanceRecords = customerStatements.stream().map(this::validateBalance).filter(Objects::nonNull)
					.collect(Collectors.toSet());
		} catch (RuntimeException ex) {
			log.error("Exception thrown while parsing the JSON request for invalid balance", ex);
			final List<ErrorRecord> errorRecords = Collections.emptyList();
			throw new CustomerStatementProcessorException(BAD_REQUEST, errorRecords);
		}
		return invalidBalanceRecords;
	}

	/**
	 * This method validates the end balance and returns the customer statements
	 * if condition is satisfied
	 * 
	 * @param stmt
	 *            customer statement
	 * @return customer statement
	 */
	private CustomerStatement validateBalance(final CustomerStatement stmt) {
		if (!getAmount(stmt.getStartBalance()).add(getAmount(stmt.getMutation()))
				.equals(getAmount(stmt.getEndBalance()))) {
			return stmt;
		} else {
			return null;
		}
	}

	/**
	 * This method formats and returns BigDecimal value for calculations
	 * 
	 * @param balance
	 *            Balance amount(Start, Mutation, end)
	 * @return formatted big decimal value
	 */
	private BigDecimal getAmount(final String balance) {
		return new BigDecimal(balance.replaceAll("[\\.]", "").replace(',', '.'));
	}

}
