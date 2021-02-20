package com.cognizant.assignment.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.assignment.StatementProcessorApplication;
import com.cognizant.assignment.TestUtils.TestUtils;
import com.cognizant.assignment.model.CustomerStatement;
import com.cognizant.assignment.model.CustomerStatementProcessorResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the integration test class for the API
 * @author Nishchal
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=StatementProcessorApplication.class,webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerStatementProcessorControllerIntegrationTest {
	
	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void givenValidPostRequest_WhenControllerIsCalled_ThenReturnsSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
		HttpEntity<List<CustomerStatement>> entity = prepareDataForSuccessfulResponse();
		
		ResponseEntity<CustomerStatementProcessorResponse> response = restTemplate.exchange(
				createURLWithPort("/customerStatementProcessor"),
				HttpMethod.POST, entity, CustomerStatementProcessorResponse.class);
		
		assertEquals("SUCCESSFUL", response.getBody().getResult());
		
	}
	
	@Test
	public void givenValidPostRequestWithIncorrectEndBalance_WhenControllerIsCalled_ThenReturnsIncorrectEndBalanceResponse() throws JsonParseException, JsonMappingException, IOException{
		
        HttpEntity<List<CustomerStatement>> entity = prepareDataForIncorrectEndBalanceResponse();
		
		ResponseEntity<CustomerStatementProcessorResponse> response = restTemplate.exchange(
				createURLWithPort("/customerStatementProcessor"),
				HttpMethod.POST, entity, CustomerStatementProcessorResponse.class);
		
		assertEquals("INCORRECT_END_BALANCE", response.getBody().getResult());
		
	}
	
	@Test
	public void givenValidPostRequestWithDuplicateRefValue_WhenControllerIsCalled_ThenReturnsDuplicateRefResponse() throws JsonParseException, JsonMappingException, IOException{
		
        HttpEntity<List<CustomerStatement>> entity = prepareDataForDuplicateReferenceResponse();
		
		ResponseEntity<CustomerStatementProcessorResponse> response = restTemplate.exchange(
				createURLWithPort("/customerStatementProcessor"),
				HttpMethod.POST, entity, CustomerStatementProcessorResponse.class);
		
		assertEquals("DUPLICATE_REFERENCE", response.getBody().getResult());
		
	}
	
	@Test
	public void givenValidPostRequestWithDuplicateRefValueAndIncorrectBalance_WhenControllerIsCalled_ThenReturnsDuplicateRefValueAndIncorrectBalanceResponse() throws JsonParseException, JsonMappingException, IOException{
		
        HttpEntity<List<CustomerStatement>> entity = prepareDataForDuplicateReferenceValueAndIncorrectBalanceResponse();
		
		ResponseEntity<CustomerStatementProcessorResponse> response = restTemplate.exchange(
				createURLWithPort("/customerStatementProcessor"),
				HttpMethod.POST, entity, CustomerStatementProcessorResponse.class);
		
		assertEquals("DUPLICATE_REFERENCE_INCORRECT_END_BALANCE", response.getBody().getResult());
		
	}
	
	@Test
	public void givenValidPostRequestWithInvalidJson_WhenControllerIsCalled_ThenReturnsBadRequestAsResponse() throws JsonParseException, JsonMappingException, IOException{
		
        HttpEntity<List<CustomerStatement>> entity = prepareDataBadRequestResponseResponse();
		
		ResponseEntity<CustomerStatementProcessorResponse> response = restTemplate.exchange(
				createURLWithPort("/customerStatementProcessor"),
				HttpMethod.POST, entity, CustomerStatementProcessorResponse.class);
		
		assertEquals("BAD_REQUEST", response.getBody().getResult());
		
	}
	
	@Test
	public void givenValidPostRequestWithEmptyJson_WhenControllerIsCalled_ThenReturnsEmptyRequestAsResponse() throws JsonParseException, JsonMappingException, IOException{
		
        HttpEntity<List<CustomerStatement>> entity = prepareDataEmptyRequestResponseResponse();
		
		ResponseEntity<CustomerStatementProcessorResponse> response = restTemplate.exchange(
				createURLWithPort("/customerStatementProcessor"),
				HttpMethod.POST, entity, CustomerStatementProcessorResponse.class);
		
		assertEquals("REQUEST_EMPTY", response.getBody().getResult());
		
	}
	
	private HttpEntity<List<CustomerStatement>> prepareDataForSuccessfulResponse() throws JsonParseException, JsonMappingException, IOException{
		
		List<CustomerStatement> custStmlist = new ArrayList<>();
		final CustomerStatement request = mapper.readValue(TestUtils.readFile("test-valid-request.json"), CustomerStatement.class);
		custStmlist = (List<CustomerStatement>) request.getAdditionalProperties().get("customerStatement");
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<>(custStmlist, headers);
		return entity;
		
	}
	
   private HttpEntity<List<CustomerStatement>> prepareDataForIncorrectEndBalanceResponse() throws JsonParseException, JsonMappingException, IOException{
		
		List<CustomerStatement> custStmlist = new ArrayList<>();
		final CustomerStatement request = mapper.readValue(TestUtils.readFile("test-invalid-balance-request.json"), CustomerStatement.class);
		custStmlist = (List<CustomerStatement>) request.getAdditionalProperties().get("customerStatement");
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<>(custStmlist, headers);
		return entity;
		
	}
   
   private HttpEntity<List<CustomerStatement>> prepareDataForDuplicateReferenceResponse() throws JsonParseException, JsonMappingException, IOException{
		
		List<CustomerStatement> custStmlist = new ArrayList<>();
		final CustomerStatement request = mapper.readValue(TestUtils.readFile("test-duplicate-ref-request.json"), CustomerStatement.class);
		custStmlist = (List<CustomerStatement>) request.getAdditionalProperties().get("customerStatement");
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<>(custStmlist, headers);
		return entity;
		
	}
   
   private HttpEntity<List<CustomerStatement>> prepareDataForDuplicateReferenceValueAndIncorrectBalanceResponse() throws JsonParseException, JsonMappingException, IOException{
		
		List<CustomerStatement> custStmlist = new ArrayList<>();
		final CustomerStatement request = mapper.readValue(TestUtils.readFile("test-invalid-balance-duplicate-ref-request.json"), CustomerStatement.class);
		custStmlist = (List<CustomerStatement>) request.getAdditionalProperties().get("customerStatement");
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<>(custStmlist, headers);
		return entity;
		
	}
   
   private HttpEntity<List<CustomerStatement>> prepareDataBadRequestResponseResponse() throws JsonParseException, JsonMappingException, IOException{
		
		List<CustomerStatement> custStmlist = new ArrayList<>();
		final CustomerStatement request = mapper.readValue(TestUtils.readFile("test-bad-request.json"), CustomerStatement.class);
		custStmlist = (List<CustomerStatement>) request.getAdditionalProperties().get("customerStatement");
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<>(custStmlist, headers);
		return entity;
		
	}
   
   private HttpEntity<List<CustomerStatement>> prepareDataEmptyRequestResponseResponse() throws JsonParseException, JsonMappingException, IOException{
		
		List<CustomerStatement> custStmlist = new ArrayList<>();
		final CustomerStatement request = mapper.readValue(TestUtils.readFile("test-empty-request.json"), CustomerStatement.class);
		custStmlist = (List<CustomerStatement>) request.getAdditionalProperties().get("customerStatement");
		HttpEntity<List<CustomerStatement>> entity = new HttpEntity<>(custStmlist, headers);
		return entity;
		
	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	

}
