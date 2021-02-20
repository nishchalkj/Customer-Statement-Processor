package com.cognizant.assignment.constants;

/**
 * This class contains constants used in the Apllication
 * @author Nishchal
 *
 */
public final class ApplicationConstants {

    public static final String SUCCESSFUL = "SUCCESSFUL";
    
    public static final String BAD_REQUEST = "BAD_REQUEST";
    
    public static final String REQUEST_EMPTY = "REQUEST_EMPTY";
   
    public static final String DUPLICATE_REFERENCE_INCORRECT_END_BALANCE = "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE";
    
    public static final String DUPLICATE_REFERENCE = "DUPLICATE_REFERENCE";
    
    public static final String INCORRECT_END_BALANCE = "INCORRECT_END_BALANCE";

    private ApplicationConstants() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate this class");
    }
    
}
