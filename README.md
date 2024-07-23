# Helphub

## Overview

Helphub is a web application designed to connect individuals seeking help with those willing to provide it. The application backend is built using the Java Spring Boot framework, ensuring a robust and scalable solution. Data is securely stored in a MySQL database.

## Features

- Create, read, update, and delete requests for help.
- User authentication and authorization.
- Secure API endpoints.
- Data storage with MySQL.
- Tested with Postman for comprehensive API validation.

## Tech Stack

- **Backend:** Java Spring Boot
- **Database:** MySQL
- **Security:** Spring Security
- **Testing:** Postman

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL Server
- Postman (for testing)

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/nithu-only/helphub.git
    cd helphub
    ```

2. **Configure MySQL:**

    - Install MySQL Server if you haven't already.
    - Create a database named `helphub_db`.
    - Create a user with appropriate permissions and set the password.

3. **Set up environment variables:**

    Create a `.env` file in the root directory and add the following:

    ```env
    SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/helphub_db
    SPRING_DATASOURCE_USERNAME=<username>
    SPRING_DATASOURCE_PASSWORD=<password>
    SPRING_JPA_HIBERNATE_DDL_AUTO=update
    ```

4. **Build the project:**

    ```bash
    mvn clean install
    ```

5. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

### API Endpoints

The API is documented using Swagger. Once the application is running, you can access the API documentation at:

http://localhost:8080/swagger-ui.html


### Testing with Postman

A Postman collection is included in the repository. Import `Helphub.postman_collection.json` into Postman to test the API endpoints.

## Security

The application uses Spring Security to secure API endpoints. Ensure that you configure proper security measures and handle sensitive data responsibly.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request.

## Contact

If you have any questions or feedback, please reach out to us at nithu.dev@yahoo.com.
