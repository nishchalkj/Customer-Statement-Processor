# Customer Statement Processor
Customer statement processor project

#Introduction

This application receives monthly delivery of customer statement records in JSON format as a POST data and returns response based on various Validations.
Please find the below table for JSON input and Validations for that.

#JSON input fields

| Field                  | Description                                      | 
|------------------------|------------------------------------------------- |
| Transaction reference  | A numeric value                                  |
| Account number         | An IBAN                                          |
| Start Balance          | The starting balance in Euros                    |
| Mutation               | Either an addition (+) or a deduction (-)        |
| Description            | Free text                                        |
| End Balance            | The end balance in Euros                         |

# Expected Output

| Http Status Code  | Condition                                                         |  Response format |
|---                |---                                                                |---               |
| 200               | When there are no duplicate reference and correct end balance     | `{"result" : "SUCCESSFUL", "errorRecords" : []}`|
| 200               | When there are duplicate reference and correct balance            |[duplicateReferenceAndcorrectBalanceJson](./duplicateReferenceAndcorrectBalance)
| 200               | When there are no duplicate reference and In correct balance      |[IncorrectBalance Json](./IncorrectBalance.json)|
| 200               | When there are duplicate reference and In correct balance         |[duplicateReferenceAndIncorrectBalanceJson]
                                                                                          (./duplicateReferenceAndIncorrectBalance.json)
| 400               | Error during parsing JSON                                         | `{"result" : "BAD_REQUEST", "errorRecords" : []}`|
| 500               | Any other situation                                               |`{"result" : "INTERNAL_SERVER_ERROR","errorRecords" : [] }`|

#Prerequisites
a.Java 8
b.Maven

#Running the project
a.In root folder, do:<pre>mvn clean install</pre>
b.After successful build, execute <pre>mvn spring-boot:run</pre>
c.Server Port Configured <pre>8082</pre>

#Design Overview
1.Separation of concern has been used for designing the layered architecture.
2.All the classes with similar concerns have been grouped same package forming an layered architecture.
3.All the classes have single responsibility and all the classes which had multiple responsibility for been separated accordingly.
4.Interface has been created and can be segregated in future to add more functionality through implementation classes.
5.Inversion of control(DI) has been used so that all the layers are loosely coupled so that implementation classes can be easily changed in future if required & also easy to     write test cases.
6.While implementing this functionality Test Driven Approach has been followed.
7.Integration Test and Unit test has been implemented.

#Implementation Overview
This application exposes below end point url

    POST:/customerStatementProcessor	
    This end point returns either successful or error result after validation based on input.
    Example : http://localhost:8082/customerStatementProcessor
    Request :      [
                     {
                      "transactionReference": "12861234578",
                      "accountNumber": "NL23BANK0897000078",
                      "startBalance": "8500.00",
                      "mutation": "-100.00",
                      "description": "Grocery",
                      "endBalance": "8400.00"
                     },
                     {
                      "transactionReference": "12861234579",
                      "accountNumber": "NL23BANK0897000478",
                      "startBalance": "7500.00",
                      "mutation": "-200.00",
                      "description": "Movie ticket",
                      "endBalance": "7300.00"
                     }
                   ]
                   
    Response for Success scenario : 
    {
                    "result": "SUCCESSFUL",
                    "errorRecords": []
                   }
                   
    Response for different Error scenario : 
    
    {
    "result": "DUPLICATE_REFERENCE",
    "errorRecords": [
        {
            "reference": "12861234578",
            "accountNumber": "NL23BANK0897000478"
        },
        {
            "reference": "12861234578",
            "accountNumber": "NL23BANK0897000078"
        }
    ]
    }    
    
    
    {
    "result": "INCORRECT_END_BALANCE",
    "errorRecords": [
        {
            "reference": "12861234579",
            "accountNumber": "NL23BANK0897000478"
        }
    ]
    }
    
    
    {
    "result": "DUPLICATE_REFERENCE_INCORRECT_END_BALANCE",
    "errorRecords": [
        {
            "reference": "12861234578",
            "accountNumber": "NL23BANK0897000078"
        },
        {
            "reference": "12861234578",
            "accountNumber": "NL23BANK0897000478"
        },
        {
            "reference": "12861234578",
            "accountNumber": "NL23BANK0897000478"
        }
    ]
    }          
    
    {
    "result": "BAD_REQUEST",
    "errorRecords": []
    }
                   


