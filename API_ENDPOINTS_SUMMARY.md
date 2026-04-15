# E-Commerce API Endpoints Summary

## Quick Reference Table

### Authentication Service (Port 8081)

| Method | Endpoint | Auth Required | Description |
|--------|----------|----------------|-------------|
| POST | `/api/v1/auth/register` | ❌ | Register new user |
| POST | `/api/v1/auth/login` | ❌ | Login and get JWT token |
| POST | `/api/v1/auth/logout/{userId}` | ✅ | Logout user |

---

### Customer Service (Port 8082)

| Method | Endpoint | Auth Required | Parameters | Description |
|--------|----------|----------------|------------|-------------|
| POST | `/api/v1/customers` | ✅ | - | Create new customer |
| GET | `/api/v1/customers` | ✅ | - | Get all customers |
| GET | `/api/v1/customers/{customer-id}` | ✅ | customer-id | Get customer by ID |
| GET | `/api/v1/customers/exists/{customer-id}` | ✅ | customer-id | Check if customer exists |
| PUT | `/api/v1/customers` | ✅ | - | Update customer |
| DELETE | `/api/v1/customers/{customer-id}` | ✅ | customer-id | Delete customer |

---

### Product Service (Port 8083)

| Method | Endpoint | Auth Required | Parameters | Description |
|--------|----------|----------------|------------|-------------|
| POST | `/api/v1/products` | ✅ | - | Create new product |
| GET | `/api/v1/products` | ✅ | - | Get all products |
| GET | `/api/v1/products/{product-id}` | ✅ | product-id | Get product by ID |
| POST | `/api/v1/products/purchase` | ✅ | - | Purchase products |

---

### Order Service (Port 8084)

| Method | Endpoint | Auth Required | Parameters | Description |
|--------|----------|----------------|------------|-------------|
| POST | `/api/v1/orders` | ✅ | - | Create new order |
| GET | `/api/v1/orders` | ✅ | - | Get all orders |
| GET | `/api/v1/orders/{order-id}` | ✅ | order-id | Get order by ID |
| GET | `/api/v1/order-lines/order/{order-id}` | ✅ | order-id | Get order line items |

---

### Payment Service (Port 8085)

| Method | Endpoint | Auth Required | Parameters | Description |
|--------|----------|----------------|------------|-------------|
| POST | `/api/v1/payments` | ✅ | - | Create/process payment |

---

## Request/Response Summary

### Authentication Requests & Responses

#### Register Request
```json
{
  "firstname": "string (required)",
  "lastname": "string (required)",
  "email": "string (required, valid email)",
  "password": "string (required)"
}
```

#### Register/Login Response
```json
{
  "userId": "string",
  "email": "string",
  "firstname": "string",
  "lastname": "string",
  "token": "string (JWT token)"
}
```

---

### Customer Requests & Responses

#### Create/Update Customer Request
```json
{
  "id": "string (optional, required for update)",
  "firstname": "string (required)",
  "lastname": "string (required)",
  "email": "string (required, valid email)",
  "address": {
    "street": "string",
    "houseNumber": "string",
    "zipCode": "string",
    "city": "string"
  }
}
```

#### Customer Response
```json
{
  "id": "string (UUID)",
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

---

### Product Requests & Responses

#### Create Product Request
```json
{
  "name": "string (required)",
  "description": "string (required)",
  "availableQuantity": "number > 0 (required)",
  "price": "decimal > 0 (required)",
  "categoryId": "integer (required)"
}
```

#### Product Response
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

#### Purchase Request (Array)
```json
[
  {
    "productId": "integer (required)",
    "quantity": "number (required)"
  }
]
```

#### Purchase Response (Array)
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

---

### Order Requests & Responses

#### Create Order Request
```json
{
  "amount": "decimal > 0 (required)",
  "paymentMethod": "string (CREDIT_CARD|PAYPAL|BANK_TRANSFER) (required)",
  "customerId": "string (required)",
  "products": [
    {
      "productId": "integer (required)",
      "quantity": "number (required)"
    }
  ]
}
```

#### Order Response
```json
{
  "id": "integer",
  "reference": "string",
  "amount": "decimal",
  "paymentMethod": "string",
  "customerId": "string"
}
```

#### Order Line Item Response
```json
{
  "id": "integer",
  "orderId": "integer",
  "productId": "integer",
  "quantity": "number",
  "price": "decimal"
}
```

---

### Payment Requests & Responses

#### Create Payment Request
```json
{
  "amount": "decimal (required)",
  "paymentMethod": "string (CREDIT_CARD|PAYPAL|BANK_TRANSFER) (required)",
  "orderId": "integer (required)",
  "orderReference": "string (required)",
  "customer": {
    "id": "string (required)",
    "firstname": "string (required)",
    "lastname": "string (required)",
    "email": "string (required)"
  }
}
```

#### Payment Response
```json
{
  "id": "integer",
  "amount": "decimal",
  "paymentMethod": "string",
  "orderId": "integer",
  "orderReference": "string"
}
```

---

## HTTP Status Codes

| Code | Status | Meaning |
|------|--------|---------|
| 200 | OK | Request successful |
| 201 | Created | Resource created (registration) |
| 202 | Accepted | Request accepted (update/delete) |
| 204 | No Content | Request successful, no content to return (logout) |
| 400 | Bad Request | Invalid input or validation error |
| 401 | Unauthorized | Missing or invalid authentication token |
| 402 | Payment Required | Payment declined or insufficient funds |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server-side error |

---

## Payment Methods

Available payment methods for orders and payments:

| Method | Description |
|--------|-------------|
| `CREDIT_CARD` | Credit/Debit card payment |
| `PAYPAL` | PayPal payment |
| `BANK_TRANSFER` | Direct bank transfer |

---

## Service Ports & URLs

| Service | Port | Base URL |
|---------|------|----------|
| Auth Service | 8081 | http://localhost:8081 |
| Customer Service | 8082 | http://localhost:8082 |
| Product Service | 8083 | http://localhost:8083 |
| Order Service | 8084 | http://localhost:8084 |
| Payment Service | 8085 | http://localhost:8085 |
| Notification Service | 8086 | http://localhost:8086 |
| API Gateway | 8080 | http://localhost:8080 |

---

## Common Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": "Customer firstname is required"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## Field Validation Rules

### Email Validation
- Must be valid email format (e.g., user@example.com)
- Required field in most endpoints

### Amount Validation
- Must be positive number
- Decimal format (e.g., 999.99)
- Required in order and payment endpoints

### Quantity Validation
- Must be positive number
- Required in purchase and order products

### Category ID Validation
- Must be valid integer
- Must reference existing category
- Required when creating products

---

## Authentication Header Format

All authenticated endpoints require:

```
Authorization: Bearer <JWT_TOKEN>
```

**Example**:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

---

## Required Fields by Endpoint

| Endpoint | Required Fields |
|----------|-----------------|
| Register | firstname, lastname, email, password |
| Login | email, password |
| Create Customer | firstname, lastname, email |
| Update Customer | id, firstname, lastname, email |
| Create Product | name, description, availableQuantity, price, categoryId |
| Create Order | amount, paymentMethod, customerId, products[] |
| Create Payment | amount, paymentMethod, orderId, orderReference, customer |

---

## Response Type Summary

| Endpoint | Response Type | Example |
|----------|---------------|---------|
| Create Customer | String (ID) | "cust-12345" |
| Create Product | Integer | 123 |
| Create Order | Integer | 456 |
| Create Payment | Integer | 789 |
| Get Customer | Object | CustomerResponse |
| Get All Customers | Array | [CustomerResponse] |
| Get Product | Object | ProductResponse |
| Get All Products | Array | [ProductResponse] |
| Get Order | Object | OrderResponse |
| Get All Orders | Array | [OrderResponse] |
| Get Order Lines | Array | [OrderLineResponse] |
| Purchase Products | Array | [ProductPurchaseResponse] |
| Check Exists | Boolean | true/false |
| Auth Response | Object | AuthResponse |

---

## Complete Workflow Status Codes

```
Register → 201 Created
Login → 200 OK
Create Customer → 200 OK
Create Product → 200 OK
Create Order → 200 OK
Create Payment → 200 OK
Update Customer → 202 Accepted
Delete Customer → 202 Accepted
Logout → 204 No Content
```

---

## Rate Limiting & Pagination

Note: Current API implementation does not include:
- Rate limiting
- Pagination (all results returned)
- Filtering
- Sorting

Consider implementing these features for production use.

---

## Security Considerations

1. **JWT Tokens**: 
   - Used for authentication
   - Included in Authorization header
   - Verify token validity on each request

2. **HTTPS**: 
   - Use HTTPS in production (not HTTP)
   - Use secure cookie flags

3. **Input Validation**:
   - All inputs validated server-side
   - Email format checked
   - Positive numbers enforced

4. **Password Security**:
   - Passwords not returned in responses
   - Passwords should be hashed server-side
   - Use strong password requirements

---

## Testing Tips

### Test Complete Flow
1. Register user → Get token
2. Create customer
3. Create product
4. Create order with customer + product
5. Process payment

### Test Error Scenarios
- Missing required fields → 400 Bad Request
- Invalid token → 401 Unauthorized
- Non-existent resource → 404 Not Found
- Invalid email format → 400 Bad Request

### Performance Testing
- Bulk create operations
- Concurrent requests
- Large response payloads

---

**For detailed documentation, see: API_DOCUMENTATION.md**

**For cURL examples, see: CURL_COMMANDS.md**

**For OpenAPI spec, see: openapi.yaml**

**Last Updated**: 2024

