# Auth Service API Routes

The Auth module now includes the following REST endpoints for user registration, login, and logout:

## Base URL
```
http://localhost:8005/api/v1/auth
```

## Endpoints

### 1. Register User
**POST** `/register`

**Request Body:**
```json
{
  "firstname": "John",
  "lastname": "Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:** (201 Created)
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "email": "john@example.com",
  "firstname": "John",
  "lastname": "Doe",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NTBlODQwMC1lMjliLTQxZDQtYTcxNi00NDY2NTU0NDAwMDAiLCJlbWFpbCI6ImpvaG5AZXhhbXBsZS5jb20iLCJmaXJzdG5hbWUiOiJKb2huIiwibGFzdG5hbWUiOiJEb2UiLCJpYXQiOjE3MTMyODE2MTksImV4cCI6MTcxMzM2ODAxOX0.aBcDeF123456"
}
```

### 2. Login User
**POST** `/login`

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:** (200 OK)
```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "email": "john@example.com",
  "firstname": "John",
  "lastname": "Doe",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NTBlODQwMC1lMjliLTQxZDQtYTcxNi00NDY2NTU0NDAwMDAiLCJlbWFpbCI6ImpvaG5AZXhhbXBsZS5jb20iLCJmaXJzdG5hbWUiOiJKb2huIiwibGFzdG5hbWUiOiJEb2UiLCJpYXQiOjE3MTMyODE2MTksImV4cCI6MTcxMzM2ODAxOX0.aBcDeF123456"
}
```

### 3. Logout User
**POST** `/logout/{userId}`

**Path Parameters:**
- `userId`: The ID of the user logging out

**Response:** (204 No Content)

## Feature Details

- **Password Encoding**: Passwords are encoded using BCrypt before storage
- **JWT Token**: Upon successful login/register, a JWT token is generated with 24-hour expiration
- **Token Claims**: 
  - `sub`: User ID
  - `email`: User email
  - `firstname`: User first name
  - `lastname`: User last name
  - `iat`: Issued at timestamp
  - `exp`: Expiration timestamp

## Error Responses

### Duplicate Email (400 Bad Request)
```json
{
  "message": "User with email john@example.com already exists"
}
```

### Invalid Credentials (400 Bad Request)
```json
{
  "message": "Invalid email or password"
}
```

### Inactive User (400 Bad Request)
```json
{
  "message": "User account is inactive"
}
```

## cURL Examples

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
curl -X POST http://localhost:8005/api/v1/auth/logout/550e8400-e29b-41d4-a716-446655440000
```

## Database Schema

The auth service uses PostgreSQL with the following `users` table:

```sql
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Configuration

Add the following to your `application.yml` to configure JWT:

```yaml
jwt:
  secret: mySecretKeyThatIsAtLeast256BitsLongForJWTTokenGenerationAndVerification
  expiration: 86400000  # 24 hours in milliseconds
```

