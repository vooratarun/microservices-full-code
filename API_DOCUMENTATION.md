# E-Commerce Microservices API Documentation

## Overview

This document provides comprehensive API documentation for the E-Commerce microservices architecture. The system consists of multiple independent services that communicate with each other to provide a complete e-commerce solution.

### Base URLs

- **Authentication Service**: `http://localhost:8081/api/v1/auth`
- **Customer Service**: `http://localhost:8082/api/v1/customers`
- **Product Service**: `http://localhost:8083/api/v1/products`
- **Order Service**: `http://localhost:8084/api/v1/orders`
- **Payment Service**: `http://localhost:8085/api/v1/payments`

### Authentication

All endpoints except registration require a valid JWT token in the `Authorization` header:

```
Authorization: Bearer <token>
```

---

## 1. Authentication Service

Base URL: `http://localhost:8081/api/v1/auth`

### 1.1 Register User

Creates a new user account.

**Endpoint**: `POST /api/v1/auth/register`

**Request Body**:
```json
{
  "firstname": "string",
  "lastname": "string",
  "email": "user@example.com",
  "password": "string"
}
```

**Required Fields**:
- `firstname`: String (required)
- `lastname`: String (required)
- `email`: Valid email address (required)
- `password`: String (required)

**Response** (201 Created):
```json
{
  "userId": "string",
  "email": "user@example.com",
  "firstname": "string",
  "lastname": "string",
  "token": "jwt-token-string"
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }'
```

---

### 1.2 Login User

Authenticates a user and returns a JWT token.

**Endpoint**: `POST /api/v1/auth/login`

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "string"
}
```

**Required Fields**:
- `email`: Valid email address (required)
- `password`: String (required)

**Response** (200 OK):
```json
{
  "userId": "string",
  "email": "user@example.com",
  "firstname": "string",
  "lastname": "string",
  "token": "jwt-token-string"
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }'
```

---

### 1.3 Logout User

Logs out a user by invalidating their session.

**Endpoint**: `POST /api/v1/auth/logout/{userId}`

**Path Parameters**:
- `userId`: String (the user ID to logout)

**Response** (204 No Content):
```
(empty)
```

**cURL Example**:
```bash
curl -X POST http://localhost:8081/api/v1/auth/logout/user-123 \
  -H "Authorization: Bearer <token>"
```

---

## 2. Customer Service

Base URL: `http://localhost:8082/api/v1/customers`

### 2.1 Create Customer

Creates a new customer record.

**Endpoint**: `POST /api/v1/customers`

**Request Body**:
```json
{
  "id": "string (optional)",
  "firstname": "string",
  "lastname": "string",
  "email": "user@example.com",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string",
    "city": "string"
  }
}
```

**Required Fields**:
- `firstname`: String (required)
- `lastname`: String (required)
- `email`: Valid email address (required)
- `address`: Address object (optional but recommended)

**Response** (200 OK):
```json
"customer-id-string"
```

**cURL Example**:
```bash
curl -X POST http://localhost:8082/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "firstname": "Jane",
    "lastname": "Smith",
    "email": "jane.smith@example.com",
    "address": {
      "street": "Main Street",
      "houseNumber": "123",
      "zipCode": "12345",
      "city": "New York"
    }
  }'
```

---

### 2.2 Get All Customers

Retrieves a list of all customers.

**Endpoint**: `GET /api/v1/customers`

**Response** (200 OK):
```json
[
  {
    "id": "string",
    "firstname": "string",
    "lastname": "string",
    "email": "user@example.com",
    "address": {
      "street": "string",
      "houseNumber": "string",
      "zipCode": "string",
      "city": "string"
    }
  }
]
```

**cURL Example**:
```bash
curl -X GET http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer <token>"
```

---

### 2.3 Get Customer by ID

Retrieves a specific customer by ID.

**Endpoint**: `GET /api/v1/customers/{customer-id}`

**Path Parameters**:
- `customer-id`: String (the customer ID)

**Response** (200 OK):
```json
{
  "id": "string",
  "firstname": "string",
  "lastname": "string",
  "email": "user@example.com",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string",
    "city": "string"
  }
}
```

**cURL Example**:
```bash
curl -X GET http://localhost:8082/api/v1/customers/cust-123 \
  -H "Authorization: Bearer <token>"
```

---

### 2.4 Check Customer Exists

Checks if a customer exists by ID.

**Endpoint**: `GET /api/v1/customers/exists/{customer-id}`

**Path Parameters**:
- `customer-id`: String (the customer ID)

**Response** (200 OK):
```json
true
```

**cURL Example**:
```bash
curl -X GET http://localhost:8082/api/v1/customers/exists/cust-123 \
  -H "Authorization: Bearer <token>"
```

---

### 2.5 Update Customer

Updates an existing customer record.

**Endpoint**: `PUT /api/v1/customers`

**Request Body**:
```json
{
  "id": "string",
  "firstname": "string",
  "lastname": "string",
  "email": "user@example.com",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string",
    "city": "string"
  }
}
```

**Response** (202 Accepted):
```
(empty)
```

**cURL Example**:
```bash
curl -X PUT http://localhost:8082/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "id": "cust-123",
    "firstname": "Jane",
    "lastname": "Doe",
    "email": "jane.doe@example.com",
    "address": {
      "street": "Second Avenue",
      "houseNumber": "456",
      "zipCode": "54321",
      "city": "Boston"
    }
  }'
```

---

### 2.6 Delete Customer

Deletes a customer record.

**Endpoint**: `DELETE /api/v1/customers/{customer-id}`

**Path Parameters**:
- `customer-id`: String (the customer ID)

**Response** (202 Accepted):
```
(empty)
```

**cURL Example**:
```bash
curl -X DELETE http://localhost:8082/api/v1/customers/cust-123 \
  -H "Authorization: Bearer <token>"
```

---

## 3. Product Service

Base URL: `http://localhost:8083/api/v1/products`

### 3.1 Create Product

Creates a new product.

**Endpoint**: `POST /api/v1/products`

**Request Body**:
```json
{
  "id": "integer (optional)",
  "name": "string",
  "description": "string",
  "availableQuantity": "number",
  "price": "decimal",
  "categoryId": "integer"
}
```

**Required Fields**:
- `name`: String (required)
- `description`: String (required)
- `availableQuantity`: Number > 0 (required)
- `price`: Decimal > 0 (required)
- `categoryId`: Integer (required)

**Response** (200 OK):
```json
123
```

**cURL Example**:
```bash
curl -X POST http://localhost:8083/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop",
    "availableQuantity": 50,
    "price": 999.99,
    "categoryId": 1
  }'
```

---

### 3.2 Get All Products

Retrieves a list of all products.

**Endpoint**: `GET /api/v1/products`

**Response** (200 OK):
```json
[
  {
    "id": 123,
    "name": "string",
    "description": "string",
    "availableQuantity": "number",
    "price": "decimal",
    "categoryId": "integer",
    "categoryName": "string",
    "categoryDescription": "string"
  }
]
```

**cURL Example**:
```bash
curl -X GET http://localhost:8083/api/v1/products \
  -H "Authorization: Bearer <token>"
```

---

### 3.3 Get Product by ID

Retrieves a specific product by ID.

**Endpoint**: `GET /api/v1/products/{product-id}`

**Path Parameters**:
- `product-id`: Integer (the product ID)

**Response** (200 OK):
```json
{
  "id": 123,
  "name": "string",
  "description": "string",
  "availableQuantity": "number",
  "price": "decimal",
  "categoryId": "integer",
  "categoryName": "string",
  "categoryDescription": "string"
}
```

**cURL Example**:
```bash
curl -X GET http://localhost:8083/api/v1/products/123 \
  -H "Authorization: Bearer <token>"
```

---

### 3.4 Purchase Products

Processes a purchase request for multiple products.

**Endpoint**: `POST /api/v1/products/purchase`

**Request Body**:
```json
[
  {
    "productId": "integer",
    "quantity": "number"
  }
]
```

**Response** (200 OK):
```json
[
  {
    "productId": "integer",
    "name": "string",
    "price": "decimal",
    "quantity": "number"
  }
]
```

**cURL Example**:
```bash
curl -X POST http://localhost:8083/api/v1/products/purchase \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '[
    {
      "productId": 123,
      "quantity": 2
    },
    {
      "productId": 456,
      "quantity": 1
    }
  ]'
```

---

## 4. Order Service

Base URL: `http://localhost:8084/api/v1/orders`

### 4.1 Create Order

Creates a new order.

**Endpoint**: `POST /api/v1/orders`

**Request Body**:
```json
{
  "id": "integer (optional)",
  "reference": "string (optional)",
  "amount": "decimal",
  "paymentMethod": "CREDIT_CARD | PAYPAL | BANK_TRANSFER",
  "customerId": "string",
  "products": [
    {
      "productId": "integer",
      "quantity": "number"
    }
  ]
}
```

**Required Fields**:
- `amount`: Decimal > 0 (required)
- `paymentMethod`: String - must be CREDIT_CARD, PAYPAL, or BANK_TRANSFER (required)
- `customerId`: String (required)
- `products`: Array of products with at least one item (required)

**Response** (200 OK):
```json
456
```

**cURL Example**:
```bash
curl -X POST http://localhost:8084/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "customerId": "cust-123",
    "products": [
      {
        "productId": 123,
        "quantity": 2
      }
    ]
  }'
```

---

### 4.2 Get All Orders

Retrieves a list of all orders.

**Endpoint**: `GET /api/v1/orders`

**Response** (200 OK):
```json
[
  {
    "id": 456,
    "reference": "string",
    "amount": "decimal",
    "paymentMethod": "CREDIT_CARD | PAYPAL | BANK_TRANSFER",
    "customerId": "string"
  }
]
```

**cURL Example**:
```bash
curl -X GET http://localhost:8084/api/v1/orders \
  -H "Authorization: Bearer <token>"
```

---

### 4.3 Get Order by ID

Retrieves a specific order by ID.

**Endpoint**: `GET /api/v1/orders/{order-id}`

**Path Parameters**:
- `order-id`: Integer (the order ID)

**Response** (200 OK):
```json
{
  "id": 456,
  "reference": "string",
  "amount": "decimal",
  "paymentMethod": "CREDIT_CARD | PAYPAL | BANK_TRANSFER",
  "customerId": "string"
}
```

**cURL Example**:
```bash
curl -X GET http://localhost:8084/api/v1/orders/456 \
  -H "Authorization: Bearer <token>"
```

---

### 4.4 Get Order Lines

Retrieves all line items for a specific order.

**Endpoint**: `GET /api/v1/order-lines/order/{order-id}`

**Path Parameters**:
- `order-id`: Integer (the order ID)

**Response** (200 OK):
```json
[
  {
    "id": "integer",
    "orderId": "integer",
    "productId": "integer",
    "quantity": "number",
    "price": "decimal"
  }
]
```

**cURL Example**:
```bash
curl -X GET http://localhost:8084/api/v1/order-lines/order/456 \
  -H "Authorization: Bearer <token>"
```

---

## 5. Payment Service

Base URL: `http://localhost:8085/api/v1/payments`

### 5.1 Create Payment

Processes a payment for an order.

**Endpoint**: `POST /api/v1/payments`

**Request Body**:
```json
{
  "id": "integer (optional)",
  "amount": "decimal",
  "paymentMethod": "CREDIT_CARD | PAYPAL | BANK_TRANSFER",
  "orderId": "integer",
  "orderReference": "string",
  "customer": {
    "id": "string",
    "firstname": "string",
    "lastname": "string",
    "email": "string"
  }
}
```

**Required Fields**:
- `amount`: Decimal (required)
- `paymentMethod`: String - must be CREDIT_CARD, PAYPAL, or BANK_TRANSFER (required)
- `orderId`: Integer (required)
- `orderReference`: String (required)
- `customer`: Customer object (required)

**Response** (200 OK):
```json
789
```

**cURL Example**:
```bash
curl -X POST http://localhost:8085/api/v1/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "orderId": 456,
    "orderReference": "ORD-20240101-001",
    "customer": {
      "id": "cust-123",
      "firstname": "John",
      "lastname": "Doe",
      "email": "john.doe@example.com"
    }
  }'
```

---

## Error Responses

All endpoints return appropriate HTTP status codes and error messages.

### Common Error Responses:

**400 Bad Request**: Invalid input or missing required fields
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": "Field validation error"
}
```

**401 Unauthorized**: Missing or invalid JWT token
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

**404 Not Found**: Resource not found
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

**500 Internal Server Error**: Server-side error
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## Data Models

### Address Object
```json
{
  "street": "string",
  "houseNumber": "string",
  "zipCode": "string",
  "city": "string"
}
```

### Customer Object
```json
{
  "id": "string",
  "firstname": "string",
  "lastname": "string",
  "email": "string",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string",
    "city": "string"
  }
}
```

### Product Object
```json
{
  "id": "integer",
  "name": "string",
  "description": "string",
  "availableQuantity": "number",
  "price": "decimal",
  "categoryId": "integer",
  "categoryName": "string",
  "categoryDescription": "string"
}
```

### Order Object
```json
{
  "id": "integer",
  "reference": "string",
  "amount": "decimal",
  "paymentMethod": "CREDIT_CARD | PAYPAL | BANK_TRANSFER",
  "customerId": "string"
}
```

### OrderLine Object
```json
{
  "id": "integer",
  "orderId": "integer",
  "productId": "integer",
  "quantity": "number",
  "price": "decimal"
}
```

### Payment Object
```json
{
  "id": "integer",
  "amount": "decimal",
  "paymentMethod": "CREDIT_CARD | PAYPAL | BANK_TRANSFER",
  "orderId": "integer",
  "orderReference": "string"
}
```

### AuthResponse Object
```json
{
  "userId": "string",
  "email": "string",
  "firstname": "string",
  "lastname": "string",
  "token": "string"
}
```

---

## Microservices Architecture

### Service Dependencies

```
Gateway (Port 8080)
├── Auth Service (Port 8081)
├── Customer Service (Port 8082)
├── Product Service (Port 8083)
├── Order Service (Port 8084)
│   └── Calls: Product Service, Customer Service
├── Payment Service (Port 8085)
│   └── Calls: Order Service
└── Notification Service (Port 8086)
    └── Receives events from: Order Service, Payment Service
```

### Inter-Service Communication

- **Order Service** calls Product Service to verify product availability and Customer Service to validate customer data
- **Payment Service** processes payments and communicates with Order Service
- **Notification Service** listens for events from Order and Payment services

---

## Testing the APIs

### Prerequisites
1. All microservices are running
2. You have the service URLs or they're accessible via the gateway
3. For authenticated endpoints, you need a valid JWT token

### Quick Start

1. **Register a new user**:
   ```bash
   curl -X POST http://localhost:8081/api/v1/auth/register \
     -H "Content-Type: application/json" \
     -d '{"firstname":"John","lastname":"Doe","email":"john@example.com","password":"test123"}'
   ```
   Save the returned `token`

2. **Create a customer**:
   ```bash
   curl -X POST http://localhost:8082/api/v1/customers \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer <token>" \
     -d '{"firstname":"Jane","lastname":"Smith","email":"jane@example.com"}'
   ```
   Save the returned customer ID

3. **Create a product**:
   ```bash
   curl -X POST http://localhost:8083/api/v1/products \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer <token>" \
     -d '{"name":"Laptop","description":"High-performance","availableQuantity":50,"price":999.99,"categoryId":1}'
   ```
   Save the returned product ID

4. **Create an order**:
   ```bash
   curl -X POST http://localhost:8084/api/v1/orders \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer <token>" \
     -d '{"amount":1999.98,"paymentMethod":"CREDIT_CARD","customerId":"<customer-id>","products":[{"productId":<product-id>,"quantity":2}]}'
   ```

5. **Process a payment**:
   ```bash
   curl -X POST http://localhost:8085/api/v1/payments \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer <token>" \
     -d '{"amount":1999.98,"paymentMethod":"CREDIT_CARD","orderId":<order-id>,"orderReference":"ORD-001","customer":{"id":"<customer-id>","firstname":"Jane","lastname":"Smith","email":"jane@example.com"}}'
   ```

---

## Support

For issues or questions regarding the API documentation, please refer to:
- README files in individual service directories
- GitHub repository documentation
- Contact the development team

---

**Last Updated**: 2024
**Version**: 1.0

