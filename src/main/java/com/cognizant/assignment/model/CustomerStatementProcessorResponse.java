package com.cognizant.assignment.model;

import java.util.List;

/**
 * This the data transfer class
 * @author Nishchal
 *
 */
public class CustomerStatementProcessorResponse {
	
	
	private String result;
	
	private List<ErrorRecord> errorRecords;

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the errorRecords
	 */
	public List<ErrorRecord> getErrorRecords() {
		return errorRecords;
	}

	/**
	 * @param errorRecords the errorRecords to set
	 */
	public void setErrorRecords(List<ErrorRecord> errorRecords) {
		this.errorRecords = errorRecords;
	}
	
	

}
