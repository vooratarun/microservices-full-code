# API Documentation Summary

This directory contains comprehensive API documentation for the E-Commerce microservices architecture. Below is a guide to each documentation file.

## 📄 Documentation Files

### 1. **API_DOCUMENTATION.md** (Main Documentation)
The primary API reference document containing:
- Complete endpoint descriptions
- Request and response schemas
- HTTP status codes
- Data models
- Inter-service communication flow
- Quick start guide
- Complete workflow examples

**Best for**: Understanding all endpoints, data structures, and the complete API

### 2. **openapi.yaml** (OpenAPI Specification)
An OpenAPI 3.0 specification file that defines:
- All endpoints with detailed descriptions
- Request/response schemas
- Security configurations
- Data models
- Tags for endpoint organization

**Best for**: 
- Generating API documentation with tools like SwaggerUI or ReDoc
- API code generation
- Automated testing

**How to use**:
```bash
# View with Swagger UI (Docker)
docker run -p 80:8080 -e SWAGGER_JSON=/api/openapi.yaml \
  -v $(pwd)/openapi.yaml:/api/openapi.yaml \
  swaggerapi/swagger-ui

# Or use online Swagger Editor
# https://editor.swagger.io/ (then load openapi.yaml)
```

### 3. **postman_collection.json** (Postman Collection)
A Postman collection containing:
- All API endpoints pre-configured
- Request templates with example data
- Authorization setup
- Variable management for reusability

**Best for**:
- Interactive API testing
- Team collaboration
- API testing workflows

**How to import**:
1. Open Postman
2. Click "Import" → "File" or "Link"
3. Select `postman_collection.json`
4. Set the `token` variable with your JWT token
5. Update other variables as needed

### 4. **CURL_COMMANDS.md** (Quick Reference)
Shell script commands for all endpoints featuring:
- Individual cURL commands for each endpoint
- Complete workflow script
- Useful cURL tips and tricks
- Error handling examples
- Service URLs reference table

**Best for**:
- Quick testing from terminal
- Shell scripting and automation
- CI/CD integration

---

## 🏗️ Microservices Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      API Gateway (Port 8080)                     │
└──────────────┬──────────────┬──────────────┬──────────────┬──────┘
               │              │              │              │
       ┌───────▼──┐    ┌──────▼──┐    ┌─────▼────┐    ┌───▼──────┐
       │   Auth   │    │ Customer │    │ Product  │    │  Order   │
       │ Service  │    │ Service  │    │ Service  │    │ Service  │
       │(8081)    │    │ (8082)   │    │ (8083)   │    │ (8084)   │
       └──────────┘    └──────────┘    └──────────┘    └────┬─────┘
                                                              │
                                                        ┌─────▼──────┐
                                                        │  Payment   │
                                                        │  Service   │
                                                        │  (8085)    │
                                                        └────────────┘
```

### Service Responsibilities

| Service | Port | Responsibility |
|---------|------|-----------------|
| **Auth** | 8081 | User authentication (register, login, logout) |
| **Customer** | 8082 | Customer profile management |
| **Product** | 8083 | Product catalog and inventory |
| **Order** | 8084 | Order management and order lines |
| **Payment** | 8085 | Payment processing |
| **Notification** | 8086 | Event notifications (asynchronous) |
| **Discovery** | N/A | Service discovery (Eureka) |
| **Config Server** | N/A | Centralized configuration |
| **Gateway** | 8080 | API Gateway (routing and load balancing) |

---

## 🔐 Authentication

### JWT Token Flow

1. **Register/Login** → Get JWT token
2. **Include token** in `Authorization` header for protected endpoints
3. **Token format**: `Bearer <token>`

```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Endpoints Without Authentication
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login

### All Other Endpoints
Require valid JWT token in Authorization header

---

## 🚀 Quick Start

### 1. Start All Services
```bash
docker-compose up -d
# or run individual microservices
```

### 2. Register a User
```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john@example.com",
    "password": "test123"
  }'
```

### 3. Login and Get Token
```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "test123"
  }'
```

### 4. Use Token for Requests
```bash
curl -X GET http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer <your-token>"
```

---

## 📋 API Endpoints by Service

### Auth Service (Port 8081)
- `POST /api/v1/auth/register` - Register user
- `POST /api/v1/auth/login` - Login user
- `POST /api/v1/auth/logout/{userId}` - Logout user

### Customer Service (Port 8082)
- `POST /api/v1/customers` - Create customer
- `GET /api/v1/customers` - List all customers
- `GET /api/v1/customers/{customer-id}` - Get customer by ID
- `GET /api/v1/customers/exists/{customer-id}` - Check if exists
- `PUT /api/v1/customers` - Update customer
- `DELETE /api/v1/customers/{customer-id}` - Delete customer

### Product Service (Port 8083)
- `POST /api/v1/products` - Create product
- `GET /api/v1/products` - List all products
- `GET /api/v1/products/{product-id}` - Get product by ID
- `POST /api/v1/products/purchase` - Purchase products

### Order Service (Port 8084)
- `POST /api/v1/orders` - Create order
- `GET /api/v1/orders` - List all orders
- `GET /api/v1/orders/{order-id}` - Get order by ID
- `GET /api/v1/order-lines/order/{order-id}` - Get order items

### Payment Service (Port 8085)
- `POST /api/v1/payments` - Create payment

---

## 🛠️ Tools & Resources

### For API Testing
- **Postman**: Import `postman_collection.json`
- **cURL**: Use commands from `CURL_COMMANDS.md`
- **Swagger UI**: Load `openapi.yaml`
- **Thunder Client**: VS Code extension (import Postman collection)
- **REST Client**: VS Code extension

### For Documentation
- **SwaggerUI**: Render `openapi.yaml`
- **ReDoc**: Alternative OpenAPI documentation viewer
- **API Blueprint**: Can convert from OpenAPI

### Command Examples

```bash
# Using Swagger UI with Docker
docker run -p 8888:8080 -e SWAGGER_JSON=/api/openapi.yaml \
  -v $(pwd)/openapi.yaml:/api/openapi.yaml \
  swaggerapi/swagger-ui

# Using ReDoc with Docker
docker run -p 8888:80 -e SPEC_URL=/api/openapi.yaml \
  -v $(pwd)/openapi.yaml:/api/openapi.yaml \
  redocly/redoc

# Pretty print cURL response
curl -s http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

---

## 📊 Data Models

### Payment Methods
- `CREDIT_CARD`
- `PAYPAL`
- `BANK_TRANSFER`

### Status Codes
- `200 OK` - Successful GET/POST request
- `201 Created` - Resource created (registration)
- `202 Accepted` - Request accepted for processing (PUT/DELETE)
- `204 No Content` - Successful DELETE request
- `400 Bad Request` - Invalid input
- `401 Unauthorized` - Missing/invalid token
- `402 Payment Required` - Payment declined
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

---

## 🔍 Common Workflows

### Complete E-Commerce Flow
1. Register/Login user → Get token
2. Create customer profile
3. Browse products
4. Create order with products
5. Get order details
6. Process payment
7. View order confirmation

### Testing a Single Service
1. Get JWT token from Auth service
2. Use token for authenticated endpoints
3. Verify request/response with documentation
4. Check error responses

---

## 📞 Support & Troubleshooting

### Token Expired?
Login again to get a new token

### 404 Error?
- Check service is running on correct port
- Verify endpoint path is correct
- Ensure resource ID exists

### 401 Unauthorized?
- Token might be expired or invalid
- Ensure token is included in Authorization header
- Format: `Bearer <token>`

### 400 Bad Request?
- Check required fields are present
- Validate email format
- Ensure amounts are positive numbers
- Verify payment method is valid

### Connection Refused?
- Ensure all services are running
- Check correct port numbers
- Verify services are accessible (firewall rules)

---

## 📚 Additional Resources

- View `API_DOCUMENTATION.md` for detailed endpoint information
- Check individual service README files for setup instructions
- Review `docker-compose.yml` for service configuration
- Check service health with: `curl http://localhost:<port>/health`

---

## 📝 File Organization

```
microservices-full-code/
├── API_DOCUMENTATION.md       ← Main documentation
├── openapi.yaml               ← OpenAPI specification
├── postman_collection.json    ← Postman collection
├── CURL_COMMANDS.md          ← cURL command reference
├── README.md                  ← This file
├── docker-compose.yml         ← Docker compose setup
└── services/
    ├── auth/
    ├── customer/
    ├── product/
    ├── order/
    ├── payment/
    ├── notification/
    ├── gateway/
    ├── discovery/
    └── config-server/
```

---

## Version Information

- **Created**: 2024
- **API Version**: 1.0
- **OpenAPI Version**: 3.0.0
- **Spring Boot Version**: 3.0.2

---

**For detailed endpoint documentation, refer to `API_DOCUMENTATION.md`**

**For cURL command examples, refer to `CURL_COMMANDS.md`**

**For integration with tools, use `openapi.yaml` or `postman_collection.json`**

