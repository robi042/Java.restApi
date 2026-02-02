# User Management REST API

A Spring Boot based REST API for user registration, authentication, and role-based authorization with JWT.

---

## Features

- User registration with password hashing
- Login & JWT token generation
- Role-based access control (via Spring Security)
- Global exception handling with consistent response format
- DTO-based request/response
- PostgreSQL (or any JDBC-compatible) database integration
- Redis (Lettuce client; optional for caching/sessions)

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


### 3. Credentials (environment variables)

All credentials come from the environment; nothing is stored in `application.properties`.

1. Copy the example env file and fill your values:
   ```bash
   cp .env.example .env
   ```
2. Edit `.env` with your DB URL, username, password, and JWT secret (min 32 chars).
3. Load env before running (or set variables in your IDE/CI):
   ```bash
   export $(grep -v '^#' .env | xargs)
   ```

**Required variables:**

| Variable        | Description                          |
|----------------|--------------------------------------|
| `DB_URL`       | JDBC URL (e.g. `jdbc:postgresql://localhost:5432/yourdb`) |
| `DB_USERNAME`  | Database username                    |
| `DB_PASSWORD`  | Database password                     |
| `JWT_SECRET`   | JWT signing key (min 32 characters)  |
| `JWT_EXPIRATION` | Optional; token expiry in ms (default `3600000`) |
| `REDIS_HOST`  | Redis host (default `localhost`)     |
| `REDIS_PORT`  | Redis port (default `6379`)          |
| `REDIS_PASSWORD` | Redis password (default `robi132` for local dev) |

Never commit `.env`; it is in `.gitignore`.

### 4. Run the Application

Using Maven wrapper: ./mvnw spring-boot:run

with Maven: mvn spring-boot:run

build JAR and run:
mvn clean package -DskipTests
java -jar target/restapi-0.0.1-SNAPSHOT.jar

### Project structure (production-oriented)

- **controller** — HTTP layer only; uses request/response DTOs, delegates to service.
- **dto** — `ApiResponse` envelope; **request/** (RegisterRequest, LoginRequest) and **response/** (AuthResponse, UserResponse) so entities are never exposed.
- **entity** — JPA entities (DB mapping only).
- **exception** — `AppException` + `ErrorCode` for consistent errors; `GlobalExceptionHandler` returns safe messages (no stack traces in production).
- **repository** — Spring Data JPA interfaces.
- **security** — JWT creation/validation (`JwtUtil`, config from env), filter, and `SecurityConfig`.
- **service** — Business logic; uses DTOs and `ErrorCode` for errors.

```
src/main/java/com/test/restapi
├── controller/
│   ├── UserController.java    # /users/* endpoints
│   └── HealthController.java # /db-test, /redis-test (public)
├── dto/
│   ├── ApiResponse.java
│   ├── request/
│   │   ├── RegisterRequest.java
│   │   └── LoginRequest.java
│   └── response/
│       ├── AuthResponse.java
│       └── UserResponse.java
├── entity/
│   └── User.java
├── exception/
│   ├── AppException.java
│   ├── ErrorCode.java
│   └── GlobalExceptionHandler.java
├── repository/
│   └── UserRepository.java
├── security/
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
├── service/
│   └── UserService.java
└── RestapiApplication.java
```
