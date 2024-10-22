# Board Application

This project is a simple board application built using **Spring Boot 3.3.1**, **Java 17**, and several essential frameworks like **Spring Data JPA**, **QueryDSL**, and **Spring Security**.

## Features

- **Spring Boot Actuator** for monitoring the application
- **Spring Data JPA** for database operations
- **QueryDSL** for tyape-safe queries
- **Spring Security** for authentication and authorization
- **Thymeleaf** for server-side rendering of web pages
- **H2** database for development, and **MySQL** for production

## Requirements

- **Java 17**
- **Gradle** as the build tool
- **MySQL** if used in production

## Getting Started

To run this application locally, follow these steps:

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd <project-directory>
    ```

2. Build the project:
    ```bash
    ./gradlew build
    ```

3. Run the application:
    ```bash
    ./gradlew bootRun
    ```

4. The application will be available at `http://localhost:8080`.

### Running Tests

To execute tests, run the following command:

```bash
./gradlew test
