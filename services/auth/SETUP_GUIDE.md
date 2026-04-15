# Auth Module Setup Guide

## Overview
The Auth module is now fully implemented as a library module with register, login, and logout endpoints. It uses JWT for token-based authentication and PostgreSQL for data storage.

## What Was Created

### Java Classes
1. **User.java** - JPA entity representing a user with fields: id, email, password, firstname, lastname, active
2. **UserRepository.java** - Spring Data JPA repository for User CRUD operations
3. **AuthService.java** - Service layer with register, login, logout, and JWT token generation/validation
4. **AuthController.java** - REST controller with three endpoints: /register, /login, /logout/{userId}
5. **RegisterRequest.java** - Request DTO for user registration with validation
6. **LoginRequest.java** - Request DTO for user login with validation
7. **AuthResponse.java** - Response DTO containing user info and JWT token
8. **PasswordEncoderConfig.java** - Spring configuration bean for BCrypt password encoding

### Configuration Files
1. **application.yml** - Spring Boot configuration with database and JWT settings
2. **V1__Create_users_table.sql** - Flyway migration script for creating the users table
3. **AUTH_API_ROUTES.md** - Complete API documentation with examples

## Prerequisites

1. PostgreSQL running on localhost:5432
2. Database "auth" created
3. Default credentials: username=alibou, password=alibou

To create the database:
```sql
CREATE DATABASE auth;
```

## Building the Project

Build the entire multi-module project:
```bash
cd /home/tarv/Desktop/springboot/springboot/microservices-full-code
mvn clean install
```

## Running Database Migrations

Run Flyway migrations for the auth service:
```bash
mvn -pl services/auth flyway:migrate
```

## API Endpoints

The auth service runs on port 8005 with base URL: `http://localhost:8005/api/v1/auth`

### Register
```bash
curl -X POST http://localhost:8005/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8005/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "securePassword123"
  }'
```

### Logout
```bash
curl -X POST http://localhost:8005/api/v1/auth/logout/{userId}
```

## Using Auth in Other Services

To use the auth module in other services (like product), add this dependency to their pom.xml:

```xml
<dependency>
    <groupId>com.alibou</groupId>
    <artifactId>auth</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

Then you can inject the AuthService:
```java
@Autowired
private AuthService authService;

// Validate a token
User user = authService.validateToken(token);
```

## Security Features

- **Password Encoding**: BCrypt with strength 10
- **JWT Token**: HS256 algorithm with 24-hour expiration
- **Token Validation**: Verify token signature and expiration
- **User Status**: Track active/inactive user status
- **Email Uniqueness**: Unique constraint on email field

## Project Structure

```
services/auth/
├── src/main/
│   ├── java/com/alibou/ecommerce/auth/
│   │   ├── AuthController.java
│   │   ├── AuthService.java
│   │   ├── AuthResponse.java
│   │   ├── LoginRequest.java
│   │   ├── PasswordEncoderConfig.java
│   │   ├── RegisterRequest.java
│   │   ├── User.java
│   │   └── UserRepository.java
│   └── resources/
│       ├── application.yml
│       └── db/migration/
│           └── V1__Create_users_table.sql
└── pom.xml
```

## Notes

- The auth module is packaged as a JAR library (not an executable app)
- It's included as a module in the parent pom.xml
- Product service can depend on it for shared authentication utilities
- JWT secrets should be changed in production environments

