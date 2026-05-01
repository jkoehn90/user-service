# User Service — Microservices System

Part of the **Microservices System** — a distributed backend built with Java, Spring Boot, and Spring Cloud.

## Overview

The User Service handles all **authentication and user management** for the Microservices System. It provides user registration and login endpoints, issues JWT tokens upon successful authentication, and integrates with Spring Security for password hashing and credential verification.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core language |
| Spring Boot 3.5.x | Application framework |
| Spring Security | Authentication and password hashing |
| Spring Data JPA | Database ORM |
| PostgreSQL | Relational database |
| JWT (jjwt 0.11.5) | Token generation and validation |
| Lombok | Boilerplate reduction |
| Netflix Eureka Client | Service discovery |

## Architecture Role

```
Client Request
      │
      ▼
API Gateway (port 8080)
      │
      ▼ routes /auth/**
User Service (port 8081)
      │
      ▼
PostgreSQL (userdb)
```

## API Endpoints

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Register a new user |
| `POST` | `/auth/login` | ❌ | Login and receive JWT token |

### Register Request
```json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@email.com",
    "password": "secret123"
}
```

### Login Request
```json
{
    "email": "john@email.com",
    "password": "secret123"
}
```

### Auth Response
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

## Security

- Passwords are hashed using **BCrypt** before storage
- JWT tokens are signed with **HS256** and expire after **24 hours**
- All non-auth endpoints require a valid JWT token
- The `JwtAuthFilter` validates tokens on every incoming request

## Project Structure

```
src/main/java/com/yourname/userservice/
├── controller/
│   └── AuthController.java
├── service/
│   └── UserService.java
├── repository/
│   └── UserRepository.java
├── entity/
│   ├── User.java
│   └── Role.java
├── dto/
│   ├── RegisterRequest.java
│   ├── LoginRequest.java
│   └── AuthResponse.java
├── security/
│   ├── JwtUtil.java
│   ├── JwtAuthFilter.java
│   └── UserDetailsServiceImpl.java
└── config/
    └── SecurityConfig.java
```

## Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL (or Docker)
- Eureka Server running on port `8761`

### Database Setup (Docker)
```bash
docker run --name userdb-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=yourpassword \
  -e POSTGRES_DB=userdb \
  -p 5432:5432 \
  -d postgres:15
```

### Running Locally
```bash
mvn spring-boot:run
```

### Configuration
Update `src/main/resources/application.yml` with your database credentials and JWT secret:
```yaml
spring:
  datasource:
    password: yourpassword
jwt:
  secret: your-256-bit-secret-key-here
```

## Related Services

| Service | Port | Repo |
|---|---|---|
| Eureka Server | 8761 | [eureka-server](../eureka-server) |
| API Gateway | 8080 | [api-gateway](../api-gateway) |
| Product Service | 8082 | [product-service](../product-service) |
| Order Service | 8083 | [order-service](../order-service) |