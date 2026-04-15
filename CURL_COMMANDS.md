# API Quick Reference - cURL Commands

This document provides quick copy-paste cURL commands for testing all API endpoints.

## Prerequisites

```bash
# Set your variables
TOKEN="your-jwt-token-here"
CUSTOMER_ID="your-customer-id-here"
PRODUCT_ID="your-product-id-here"
ORDER_ID="your-order-id-here"
USER_ID="your-user-id-here"
```

---

## Authentication Endpoints

### 1. Register User
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

### 2. Login User
```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "securePassword123"
  }'
```

### 3. Logout User
```bash
curl -X POST http://localhost:8081/api/v1/auth/logout/$USER_ID \
  -H "Authorization: Bearer $TOKEN"
```

---

## Customer Endpoints

### 1. Create Customer
```bash
curl -X POST http://localhost:8082/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
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

### 2. Get All Customers
```bash
curl -X GET http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Get Customer by ID
```bash
curl -X GET http://localhost:8082/api/v1/customers/$CUSTOMER_ID \
  -H "Authorization: Bearer $TOKEN"
```

### 4. Check Customer Exists
```bash
curl -X GET http://localhost:8082/api/v1/customers/exists/$CUSTOMER_ID \
  -H "Authorization: Bearer $TOKEN"
```

### 5. Update Customer
```bash
curl -X PUT http://localhost:8082/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "id": "'$CUSTOMER_ID'",
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

### 6. Delete Customer
```bash
curl -X DELETE http://localhost:8082/api/v1/customers/$CUSTOMER_ID \
  -H "Authorization: Bearer $TOKEN"
```

---

## Product Endpoints

### 1. Create Product
```bash
curl -X POST http://localhost:8083/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Laptop",
    "description": "High-performance laptop with 16GB RAM",
    "availableQuantity": 50,
    "price": 999.99,
    "categoryId": 1
  }'
```

### 2. Get All Products
```bash
curl -X GET http://localhost:8083/api/v1/products \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Get Product by ID
```bash
curl -X GET http://localhost:8083/api/v1/products/$PRODUCT_ID \
  -H "Authorization: Bearer $TOKEN"
```

### 4. Purchase Products
```bash
curl -X POST http://localhost:8083/api/v1/products/purchase \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
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

## Order Endpoints

### 1. Create Order
```bash
curl -X POST http://localhost:8084/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "customerId": "'$CUSTOMER_ID'",
    "products": [
      {
        "productId": 123,
        "quantity": 2
      }
    ]
  }'
```

### 2. Get All Orders
```bash
curl -X GET http://localhost:8084/api/v1/orders \
  -H "Authorization: Bearer $TOKEN"
```

### 3. Get Order by ID
```bash
curl -X GET http://localhost:8084/api/v1/orders/$ORDER_ID \
  -H "Authorization: Bearer $TOKEN"
```

### 4. Get Order Line Items
```bash
curl -X GET http://localhost:8084/api/v1/order-lines/order/$ORDER_ID \
  -H "Authorization: Bearer $TOKEN"
```

---

## Payment Endpoints

### 1. Create Payment
```bash
curl -X POST http://localhost:8085/api/v1/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "orderId": '$ORDER_ID',
    "orderReference": "ORD-20240101-001",
    "customer": {
      "id": "'$CUSTOMER_ID'",
      "firstname": "John",
      "lastname": "Doe",
      "email": "john.doe@example.com"
    }
  }'
```

---

## Payment Methods

Valid payment methods for order and payment endpoints:
- `CREDIT_CARD`
- `PAYPAL`
- `BANK_TRANSFER`

---

## Complete Workflow Example

```bash
#!/bin/bash

# 1. Register and login
echo "=== Registering User ==="
REGISTER_RESPONSE=$(curl -s -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Test",
    "lastname": "User",
    "email": "test@example.com",
    "password": "test123"
  }')

TOKEN=$(echo $REGISTER_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Token: $TOKEN"

# 2. Create a customer
echo -e "\n=== Creating Customer ==="
CUSTOMER_RESPONSE=$(curl -s -X POST http://localhost:8082/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "firstname": "Jane",
    "lastname": "Smith",
    "email": "jane@example.com",
    "address": {
      "street": "Main St",
      "houseNumber": "123",
      "zipCode": "12345",
      "city": "NYC"
    }
  }')

CUSTOMER_ID=$(echo $CUSTOMER_RESPONSE | tr -d '"')
echo "Customer ID: $CUSTOMER_ID"

# 3. Create a product
echo -e "\n=== Creating Product ==="
PRODUCT_RESPONSE=$(curl -s -X POST http://localhost:8083/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "Laptop",
    "description": "High-performance",
    "availableQuantity": 50,
    "price": 999.99,
    "categoryId": 1
  }')

PRODUCT_ID=$(echo $PRODUCT_RESPONSE)
echo "Product ID: $PRODUCT_ID"

# 4. Create an order
echo -e "\n=== Creating Order ==="
ORDER_RESPONSE=$(curl -s -X POST http://localhost:8084/api/v1/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "customerId": "'$CUSTOMER_ID'",
    "products": [
      {
        "productId": '$PRODUCT_ID',
        "quantity": 2
      }
    ]
  }')

ORDER_ID=$(echo $ORDER_RESPONSE)
echo "Order ID: $ORDER_ID"

# 5. Process payment
echo -e "\n=== Processing Payment ==="
PAYMENT_RESPONSE=$(curl -s -X POST http://localhost:8085/api/v1/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "orderId": '$ORDER_ID',
    "orderReference": "ORD-001",
    "customer": {
      "id": "'$CUSTOMER_ID'",
      "firstname": "Jane",
      "lastname": "Smith",
      "email": "jane@example.com"
    }
  }')

PAYMENT_ID=$(echo $PAYMENT_RESPONSE)
echo "Payment ID: $PAYMENT_ID"

echo -e "\n=== Workflow Complete ==="
```

---

## Useful Tips

### Pretty Print JSON Response
```bash
curl -s http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

### Save Response to File
```bash
curl -X GET http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN" \
  > customers.json
```

### Extract Specific Field with jq
```bash
curl -s http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN" | jq '.[0].id'
```

### Set Long Timeout (useful for slow networks)
```bash
curl --max-time 30 http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN"
```

### Verbose Output (shows headers and more details)
```bash
curl -v http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN"
```

### Include Response Headers
```bash
curl -i http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN"
```

---

## Error Handling

### Common Error Responses

**401 Unauthorized - Missing or Invalid Token**
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

**400 Bad Request - Validation Error**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Customer Email is required"
}
```

**404 Not Found**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

---

## Service URLs

| Service | Port | Base URL |
|---------|------|----------|
| Auth | 8081 | http://localhost:8081 |
| Customer | 8082 | http://localhost:8082 |
| Product | 8083 | http://localhost:8083 |
| Order | 8084 | http://localhost:8084 |
| Payment | 8085 | http://localhost:8085 |
| Gateway | 8080 | http://localhost:8080 |
| Notification | 8086 | http://localhost:8086 |

---

## Notes

- Replace `$TOKEN`, `$CUSTOMER_ID`, `$PRODUCT_ID`, `$ORDER_ID` with actual values
- All endpoints except `/register` and `/login` require a valid JWT token
- Amounts are in decimal format (e.g., 999.99)
- Product IDs are integers
- Customer IDs and User IDs are strings (UUIDs)
- Ensure all services are running before making requests

---

**Last Updated**: 2024
**Version**: 1.0

