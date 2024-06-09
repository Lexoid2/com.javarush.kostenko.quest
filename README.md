# Text Quest Project

This project is a small text-based quest game. At each step, the player is presented with a question, and the next question depends on the previous answer. The goal is to create an engaging and interactive story where the player's choices determine the progression and outcome of the quest.

## Overview

In this quest, the player navigates through various scenarios by making decisions at each step. Each decision leads to different outcomes and further questions. The quest aims to provide an immersive and interactive experience, encouraging players to explore different paths and consequences.

## Deployment

It is recommended to run the application using Tomcat 9. When deploying, use the context from the project root `/`.

## Project Structure

- **Controller**: Handles the HTTP requests and directs the flow of the game.
- **Model**: Represents the data structure of questions and options.
- **Repository**: Manages the retrieval and storage of questions from the JSON file.
- **Service**: Contains the business logic for fetching questions and processing user choices.

## How to Run

1. Убедитесь, что у вас установлена Java 8.
2. Сборка проекта с использованием Maven:
    ```bash
    mvn clean install
    ```
3. Разверните файл WAR в Tomcat 9.
4. Доступ к приложению осуществляется по адресу `http://localhost:8080/`.

## Key Files
- **StartAdventureServlet.java:** The main servlet that handles the game logic.
- **Question.java:** Model class representing a question and its possible options.
- **QuestionRepository.java:** Repository class that loads questions from a JSON file.
- **QuestionService.java:** Service class that provides question-related functionalities.
- **questions.json:** JSON file containing the questions and options for the quest.

## Testing
The project includes unit tests written using JUnit 5 and Mockito. To run the tests, use: 
```bash
mvn test
```

## Dependencies
The project uses the following dependencies:

- **Servlet API**: For handling HTTP requests.
- **JSTL**: For JSP support.
- **Jackson Databind**: For JSON parsing.
- **Lombok**: For reducing boilerplate code.
- **JUnit 5**: For unit testing.
- **Mockito**: For mocking in tests.
- **SLF4J**: For logging.
- **Log4j 2**: For logging implementation.

## License
This project is licensed under the MIT License.
