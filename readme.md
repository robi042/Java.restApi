# User Management REST API

A Spring Boot based REST API for user registration, authentication, and role-based authorization with JWT.

---

## Features

- User registration with password hashing
- Login & JWT token generation
- Role-based access control (via Spring Security)
- Global exception handling with consistent response format
- DTO-based request/response
- MySQL (or any JDBC-compatible) database integration

---

## Prerequisites

- **Java 17** or later
- **Maven 3.8+**
- **MySQL 8+** (or PostgreSQL with minor config changes)
- Any IDE (IntelliJ IDEA / Eclipse / VS Code)

---

## Project Setup

### 1. Clone the Repository

```bash
git clone https://github.com/robi042/Java.restApi.git
```
### 2. Install Dependencies

```./mvnw clean install ```


Or if Maven is installed globally

```mvn clean install```


### 3. Database Configuration

Create a new database in PostgreSQL/MySQL/MsSQL/Oracle

Update src/main/resources/application.properties with database credential


### 4. Run the Application

Using Maven wrapper: ./mvnw spring-boot:run

with Maven: mvn spring-boot:run

build JAR and run:
mvn clean package -DskipTests
java -jar target/restapi-0.0.1-SNAPSHOT.jar

### Folder Details

controller → Handles HTTP requests, calls services, and returns responses.

dto → Defines request and response objects to avoid exposing entity directly.

entity → Maps Java classes to database tables (using JPA/Hibernate).

exception → Centralized error handling and custom exceptions.

repository → Interfaces extending JpaRepository for database access.

security → Manages JWT authentication, filters, and role-based authorization.

service → Contains business logic, interacts with repositories.

```declarative
src/main/java/com/test/restapi
│
├── controller      # REST controllers (API endpoints)
│   └── UserController.java
│
├── dto             # Data Transfer Objects (request/response payloads)
│   └── ApiResponse.java
│   └── UserRequestDto.java
│   └── UserResponseDto.java
│
├── entity          # JPA entities (database tables mapping)
│   └── User.java
│
├── exception       # Global & custom exception handling
│   └── AppException.java
│   └── GlobalExceptionHandler.java
│
├── repository      # Spring Data JPA repositories
│   └── UserRepository.java
│
├── security        # Security config & JWT utilities
│   └── JwtUtil.java
│   └── SecurityConfig.java
│   └── JwtAuthenticationFilter.java
│
├── service         # Business logic layer
│   └── UserService.java
│   └── UserServiceImpl.java
│
└── RestApiApplication.java  # Main application class

```
