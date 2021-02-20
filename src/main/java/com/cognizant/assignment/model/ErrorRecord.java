package com.cognizant.assignment.model;

import java.io.Serializable;

/**
 * This class contains the info of error records
 * @author Nishchal
 *
 */
public class ErrorRecord implements Serializable {
	

	private static final long serialVersionUID = 1361335271978443149L;

	private String reference;

	private String accountNumber;

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	

}
