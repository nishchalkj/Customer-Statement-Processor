package com.cognizant.assignment.exception;

import java.util.List;

import com.cognizant.assignment.model.ErrorRecord;

/**
 * Custom Customer statement processor exception class.
 *
 * @author Nishchal
 */
public class CustomerStatementProcessorException extends Exception {

	private static final long serialVersionUID = -9163557384117743228L;

	private final String message;

	private final List<ErrorRecord> errorRecords;

	/**
	 * {@link CustomerStatementProcessorException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param errorRecords
	 *            list of {@link ErrorRecord}s
	 */
	public CustomerStatementProcessorException(final String message, final List<ErrorRecord> errorRecords) {
		this.message = message;
		this.errorRecords = errorRecords;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the errorRecords
	 */
	public List<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}

}
