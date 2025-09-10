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
