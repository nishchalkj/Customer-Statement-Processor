package com.cognizant.assignment.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.assignment.exception.CustomerStatementProcessorException;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.CustomerStatementProcessorResponse;
import com.cognizant.assignment.service.CustomerStatementProcessorService;

/**
 * This is the controller class for the customer statement processor API
 * @author Nishchal
 *
 */
@RestController
public class CustomerStatementProcessorController {
	
	@Autowired
	private CustomerStatementProcessorService customerStatementService;
	
	private static final Logger log = LoggerFactory.getLogger(CustomerStatementProcessorController.class);
	
    /**
     * This is the controller method which send request to service layer and returns json Response
     * @param request contains list of customer statements
     * @return validated json response
     * @throws CustomerStatementProcessorException customer Statement processor Exception
     */
    @PostMapping(value = "/customerStatementProcessor")
    public ResponseEntity<CustomerStatementProcessorResponse> processStatements(@Valid @RequestBody final List<CustomerStatement> request)
            throws CustomerStatementProcessorException {
        log.debug("Calling customer statement validator service ");
        final CustomerStatementProcessorResponse response = customerStatementService.processStatement(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
