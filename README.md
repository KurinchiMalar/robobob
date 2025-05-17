# RoboBob API

## Overview

RoboBob is a Spring Boot application which serves as a question-answering API. It can handle predefined questions, arithmetic expressions, and provide fallback responses for questions that it is not pre-trained. 
The API is designed with a clean architecture following SOLID principles and employs Strategy and Chain of Responsibility patterns for handling different types of questions.

## Features

- **Predefined Question Answering**: Answers questions from pre-defined set of questions and answers loaded from a JSON file.
- **Arithmetic Expression Computing**: Computes arithmetic expressions when asked in natural language. 
  - Supported operation : addition(+), subtraction(-), multiplication(*) , division(/)
  - Handles expressions with parantheses and decimal numbers. 
- **Caching**: Implements Caching of responses for improved performance. 
- **Error Handling**: Error handling achieved with appropriated HTTP status codes. 
- **Input Validation**: Validates input requests and provides reasoning on validation failure. 
- **API Documentation: Interactive API documentation via Swagger UI

## Architecture

The application follows a layered architecture: 
- **API Layer**: Controllers, DTOs, Exception handlers
- **Service Layer**: Core business logic with strategy pattern implementation
- **Repository Layer**: Data access layer for retrieving predefined questions and answers

### Design Patterns Used

- **Strategy Pattern**: Different strategies are available to answer based on question type and can be extended in future based on requirement. 
- **Chain of Responsibility**: Delegates a question through multiple strategies until one strategy can answer it. 
- **Repository Pattern**: Abstracts data access
- **Caching**: Improves performance by caching answers to previously asked questions. 

## API Endpoints

### Ask a Question
```
POST /api/v1/robobob/ask
```

#### Request Body
```json
{
  "question": "What is 5 + 3?"
}
```

#### Response
```json
{
  "answer": "Answer is: 8",
  "timeStamp": 1747492289003
}
```
## API Documentation

The API is documented using Swagger/OpenAPI:

- **Swagger UI**: Can be accessed at `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Spec**: Raw API specification accessed at `http://localhost:8080/v3/api-docs`

## Error Responses

The API returns appropriate error responses with details:

```json
{
  "answer": "Sorry! Invalid Arithmetic Expression: java.lang.ArithmeticException: Division by zero is not allowed",
  "timeStamp": 1747497371900
}
```
## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher

### Running the Application

1. Clone the repository
    ```
    git clone https://github.com/KurinchiMalar/robobob.git 
    ```
2. Navigate to the project directory
   ```
   cd robobob
   ```
3. Switch to master branch
    ```
   git checkout -b master remotes/origin/master
   ```
4. Pull all the latest changes 
   ```
   git pull origin master
   ```
5. Run the application
    ```
    java -jar target/robobob-1.0.0-SNAPSHOT.jar
    ```
The application will start on port 8080 by default.

### Configuration

Configuration properties are defined in `application.properties`:

```properties
server.port=8080
spring.application.name=robobob-api
robobob.questions.file=questions.json
```

You can modify the port or the questions file path as needed.

## Examples

### Predefined Questions

Request:
```json
{
  "question": "What is your name?"
}
```

Response:
```json
{
  "answer": "My name is RoboBob!",
  "timeStamp": 1747497371900
}
```

### Arithmetic Questions

Request:
```json
{
  "question": "Calculate 10 + 20 * (30 - 5)"
}
```

Response:
```json
{
  "answer": "Answer is: 510",
  "timeStamp": 1747498580230
}
```

### Adding new Predefined Questions

To add new predefined questions, update the `questions.json` file with new key-value pairs:
```json
{
  "how is the weather": "It is bright and sunny today!",
  "are you okay": "yes I am, thankyou"
}
```
## Project Structure

```
|-----api
|     |----config
|     |----constants
|     |----controller
|     |----dto
|     |----exception
|------service
       |----core
       |    |--repo
       |----impl
       |    |--evaluator
       |    |--repo
       |----strategy
       |    |--impl
       |----util
```
## Extending the Application

### Adding New Strategies

1. New Class implementing `QAStrategy` interface has to be created. 
2. `@Order` priority has to be adjusted according to the new application needs.

```java
import com.provenir.challenge.robobob.service.strategy.QAStrategy;
import org.springframework.stereotype.Component;

@Component
@Order(3) // Set priority accordingly
public class NewQAStrategy implements QAStrategy {
    @Override
    public boolean isAnswerable(String question){
        // Logic to determine if this strategy can answer the question. 
    }
    @Override
    public String answer(String question){
        // Logic to generate the answer using this strategy
    }
}

```
##Testing

The application has Unit, Integration and Controller Tests. The Test suite components ensure complete coverage and code quality standards have been ensured. 

### Test Classes

#### Controller Tests
- `com.provenir.challenge.robobob.api.controller.RobobobControllerTest`

#### Exception Handler Tests
- `com.provenir.challenge.robobob.api.exception.GlobalExceptionHandlerTest`

#### Service Tests
- `com.provenir.challenge.robobob.service.impl.DefaultQuestionServiceTest`
- `com.provenir.challenge.robobob.service.impl.evaluator.ArithmeticExpressionEvaluatorTest`
- `com.provenir.challenge.robobob.service.impl.repo.FileBasedRepositoryTest`

#### Strategy Tests
- `com.provenir.challenge.robobob.service.strategy.impl.ArithmeticQAStrategyTest`
- `com.provenir.challenge.robobob.service.strategy.impl.FallbackQAStrategyTest`
- `com.provenir.challenge.robobob.service.strategy.impl.PredefinedQAStrategyTest`

#### Integration Tests
- `com.provenir.challenge.robobob.integration.RobobobIntegrationTest`
- `com.provenir.challenge.robobob.api.RobobobApplicationTests`

To run the test suite:

```bash
mvn test
```
To generate a test coverage report:

```bash
mvn verify
```
### Adding New Tests

When extending the application with new features:

1. Create corresponding test calss following same package structure as source class.
2. Add appropriate Junit assertion to validate expected behavior. 
3. Add Controller tests, Integration tests and ensure exists tests pass.

## Contributors
- [Kurinchi Malar]