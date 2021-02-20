package com.cognizant.assignment.service.implementation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cognizant.assignment.exception.CustomerStatementProcessorException;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.CustomerStatementProcessorResponse;
import com.cognizant.assignment.utils.StatementProcessorUtils;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CustomerStatementProcessorServiceImplTest {

	@InjectMocks
	CustomerStatementProcessorServiceImpl serviceImpl;

	@Mock
	StatementProcessorUtils statementProcessor;

	@Test
	public void givenValidRequest_WhenMethodIsCalled_ThenReturnsSuccessfulResponse()
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = prepareDataForSuccessfulResponse(2);

		CustomerStatementProcessorResponse response = serviceImpl.processStatement(request);

		assertEquals("SUCCESSFUL", response.getResult());
	}

	@Test
	public void givenValidRequestWithIncorrectEndBalance_WhenMethodIsCalled_ThenReturnsIncorrectEndBalanceResponse()
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = prepareDataForIncorrectEndBalanceResponse(2);

		CustomerStatementProcessorResponse response = serviceImpl.processStatement(request);

		assertEquals("INCORRECT_END_BALANCE", response.getResult());
	}

	@Test
	public void givenValidRequestWithDuplicateRefValue_WhenMethodIsCalled_ThenReturnsDuplicateRefResponse()
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = prepareDataForDuplicateReferenceResponse(2);

		CustomerStatementProcessorResponse response = serviceImpl.processStatement(request);

		assertEquals("DUPLICATE_REFERENCE", response.getResult());
	}

	@Test
	public void givenValidRequestWithDuplicateRefValueAndIncorrectBalance_WhenMethodIsCalled_ThenReturnsDuplicateRefValueAndIncorrectBalanceResponse()
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = prepareDataForDuplicateReferenceValueAndIncorrectBalanceResponse(2);

		CustomerStatementProcessorResponse response = serviceImpl.processStatement(request);

		assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", response.getResult());
	}

	private List<CustomerStatement> prepareDataForSuccessfulResponse(int count)
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
		when(statementProcessor.getDuplicates(Mockito.anyList())).thenReturn(new HashSet<>());
		when(statementProcessor.getInvalidBalanceRecords(Mockito.anyList())).thenReturn(new HashSet<>());
		return request;
	}

	private List<CustomerStatement> prepareDataForIncorrectEndBalanceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		Set<CustomerStatement> mockedResponse = new HashSet<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference(String.valueOf(i));
			statement.setAccountNumber("NL40ABNA012345678");
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance(String.valueOf(i));
			request.add(statement);
			mockedResponse.add(statement);
		}
		when(statementProcessor.getDuplicates(Mockito.anyList())).thenReturn(new HashSet<>());
		when(statementProcessor.getInvalidBalanceRecords(Mockito.anyList())).thenReturn(mockedResponse);
		return request;
	}

	private List<CustomerStatement> prepareDataForDuplicateReferenceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		Set<CustomerStatement> mockedResponse = new HashSet<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference(String.valueOf(i));
			statement.setAccountNumber("NL40ABNA012345678");
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance("900");
			request.add(statement);
			mockedResponse.add(statement);
		}
		when(statementProcessor.getDuplicates(Mockito.anyList())).thenReturn(mockedResponse);
		when(statementProcessor.getInvalidBalanceRecords(Mockito.anyList())).thenReturn(new HashSet<>());
		return request;
	}

	private List<CustomerStatement> prepareDataForDuplicateReferenceValueAndIncorrectBalanceResponse(int count)
			throws CustomerStatementProcessorException {

		List<CustomerStatement> request = new ArrayList<>();
		Set<CustomerStatement> mockedResponse = new HashSet<>();
		for (int i = 0; i < count; i++) {
			CustomerStatement statement = new CustomerStatement();
			statement.setTransactionReference(String.valueOf(i));
			statement.setAccountNumber("NL40ABNA012345678");
			statement.setDescription("Movie Ticket");
			statement.setStartBalance("1000");
			statement.setMutation("-100");
			statement.setEndBalance(String.valueOf(i));
			request.add(statement);
			mockedResponse.add(statement);
		}
		when(statementProcessor.getDuplicates(Mockito.anyList())).thenReturn(mockedResponse);
		when(statementProcessor.getInvalidBalanceRecords(Mockito.anyList())).thenReturn(mockedResponse);
		return request;
	}

}
