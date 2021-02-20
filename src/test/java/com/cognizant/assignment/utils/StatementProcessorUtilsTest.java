package com.cognizant.assignment.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.assignment.exception.CustomerStatementProcessorException;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.CustomerStatementProcessorResponse;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class StatementProcessorUtilsTest {
	
	
	@InjectMocks
	private StatementProcessorUtils statementProcessor;
	
	@Test
	public void givenValidRequestWithIncorrectEndBalance_WhenMethodIsCalled_ThenReturnsIncorrectEndBalanceResponse() throws CustomerStatementProcessorException{
		
		List<CustomerStatement> request = prepareDataForIncorrectEndBalanceResponse(2);

		Set<CustomerStatement> response = statementProcessor.getInvalidBalanceRecords(request);
		
		assertEquals(2, response.size());
	}
	
	@Test
	public void givenValidRequestWithCorrectEndBalance_WhenMethodIsCalled_ThenReturnsEmptyIncorrectEndBalanceResponse() throws CustomerStatementProcessorException{
		
		List<CustomerStatement> request = prepareDataWithcorrectEndBalanceResponse(2);

		Set<CustomerStatement> response = statementProcessor.getInvalidBalanceRecords(request);
		
		assertTrue(response.isEmpty());
	}
	
	@Test
	public void givenValidRequestWithDuplicateRefValue_WhenMethodIsCalled_ThenReturnsDuplicateRefResponse() throws CustomerStatementProcessorException{
		
		List<CustomerStatement> request = prepareDataForDuplicateReferenceResponse(2);

		Set<CustomerStatement> response = statementProcessor.getDuplicates(request);
		
		assertEquals(2, response.size());
	}
	
	@Test
	public void givenValidRequestWithOutDuplicateRefValue_WhenMethodIsCalled_ThenReturnsEmptyDuplicateRefResponse() throws CustomerStatementProcessorException{
		
		List<CustomerStatement> request = prepareDataForWithOutDuplicateReferenceResponse(2);

		Set<CustomerStatement> response = statementProcessor.getDuplicates(request);
		
		assertTrue(response.isEmpty());
	}
	
	@Test(expected = CustomerStatementProcessorException.class)
	public void givenValidRequestWithEmptyRequest_WhenMethodIsCalled_ThenReturnsEmptyRequestAsResponse()
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = prepareDataBadRequestResponseResponse();

		statementProcessor.validateInputStatements(request);

	}
	
	private List<CustomerStatement> prepareDataForIncorrectEndBalanceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference(String.valueOf(i));
			statement.setAccountNumber("NL40ABNA012345678");
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance(String.valueOf(i));
			request.add(statement);
		}
		return request;
	}
	
	private List<CustomerStatement> prepareDataWithcorrectEndBalanceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference(String.valueOf(i));
			statement.setAccountNumber("NL40ABNA012345678");
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance("900");
			request.add(statement);
		}
		return request;
	}
	
	private List<CustomerStatement> prepareDataForDuplicateReferenceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference("123456");
			statement.setAccountNumber("NL40ABNA012345678"+i);
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance("900");
			request.add(statement);
		}
		return request;
	}
	
	private List<CustomerStatement> prepareDataForWithOutDuplicateReferenceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference(String.valueOf(i));
			statement.setAccountNumber("NL40ABNA012345678");
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance("900");
			request.add(statement);
		}
		return request;
	}
	
	private List<CustomerStatement> prepareDataBadRequestResponseResponse() throws CustomerStatementProcessorException {
        List<CustomerStatement> request = new ArrayList<>();
		return request;
	}

}
