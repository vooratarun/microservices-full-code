# API Developer's Getting Started Guide

Welcome to the E-Commerce Microservices API! This guide will help you get started with testing and integrating with our APIs.

---

## 📚 Documentation Files Overview

We provide API documentation in multiple formats to suit your needs:

| File | Format | Best For |
|------|--------|----------|
| **API_DOCUMENTATION.md** | Markdown | Complete reference, detailed descriptions |
| **API_ENDPOINTS_SUMMARY.md** | Markdown Table | Quick lookup, field requirements |
| **openapi.yaml** | OpenAPI 3.0 | Swagger UI, code generation |
| **postman_collection.json** | JSON | Interactive testing with Postman |
| **CURL_COMMANDS.md** | Shell Commands | Terminal testing, automation |
| **API_DOCS_README.md** | Markdown | Overview and navigation guide |

---

## 🚀 Quick Start (5 minutes)

### Step 1: Start Services
```bash
# Using Docker Compose (recommended)
docker-compose up -d

# Or start individual services
cd services/auth && mvn spring-boot:run
```

### Step 2: Register User
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

**Response** (save the token):
```json
{
  "userId": "uuid-123",
  "email": "john@example.com",
  "firstname": "John",
  "lastname": "Doe",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Step 3: Test Authenticated Endpoint
```bash
# Replace TOKEN with your JWT token
curl -X GET http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer TOKEN"
```

---

## 🛠️ Choose Your Testing Tool

### Option 1: Postman (Recommended for Beginners)

1. **Download Postman**: https://www.postman.com/downloads/
2. **Import Collection**:
   - File → Import
   - Select `postman_collection.json`
   - Click Import
3. **Set Variables**:
   - Click Environment → Create
   - Add `token` variable
   - Paste JWT token from login response
4. **Test Endpoints**:
   - Select endpoint
   - Click Send

**Video Tutorial**: [Postman Basics](https://www.youtube.com/watch?v=vp-Eea8rW2s)

### Option 2: Swagger UI (Great for Visualization)

```bash
# Using Docker
docker run -p 8888:8080 \
  -e SWAGGER_JSON=/api/openapi.yaml \
  -v $(pwd)/openapi.yaml:/api/openapi.yaml \
  swaggerapi/swagger-ui

# Then visit http://localhost:8888
```

### Option 3: cURL (Best for CLI/Automation)

```bash
# Set variables
TOKEN="your-jwt-token"
CUSTOMER_ID="customer-uuid"

# Test endpoint
curl -X GET http://localhost:8082/api/v1/customers/$CUSTOMER_ID \
  -H "Authorization: Bearer $TOKEN"
```

### Option 4: VS Code REST Client Extension

1. Install "REST Client" extension by Huachao Mao
2. Create file `test.http`:
```http
### Register
POST http://localhost:8081/api/v1/auth/register
Content-Type: application/json

{
  "firstname": "John",
  "lastname": "Doe",
  "email": "john@example.com",
  "password": "test123"
}

### Get Customers
GET http://localhost:8082/api/v1/customers
Authorization: Bearer {{token}}
```
3. Click "Send Request" above each request

---

## 📋 Complete Workflow Example

### Scenario: Create Order and Process Payment

```bash
#!/bin/bash

echo "=== 1. Register User ==="
REGISTER=$(curl -s -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "Alice",
    "lastname": "Smith",
    "email": "alice@example.com",
    "password": "secure123"
  }')

TOKEN=$(echo $REGISTER | grep -o '"token":"[^"]*' | cut -d'"' -f4)
echo "Token: $TOKEN"

echo -e "\n=== 2. Create Customer ==="
CUSTOMER=$(curl -s -X POST http://localhost:8082/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "firstname": "Alice",
    "lastname": "Smith",
    "email": "alice@example.com",
    "address": {
      "street": "Main St",
      "houseNumber": "100",
      "zipCode": "10001",
      "city": "New York"
    }
  }')

CUSTOMER_ID=$(echo "$CUSTOMER" | tr -d '"')
echo "Customer ID: $CUSTOMER_ID"

echo -e "\n=== 3. Create Product ==="
PRODUCT=$(curl -s -X POST http://localhost:8083/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "name": "iPhone 14",
    "description": "Latest Apple smartphone",
    "availableQuantity": 100,
    "price": 999.99,
    "categoryId": 1
  }')

PRODUCT_ID=$(echo $PRODUCT)
echo "Product ID: $PRODUCT_ID"

echo -e "\n=== 4. Create Order ==="
ORDER=$(curl -s -X POST http://localhost:8084/api/v1/orders \
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

ORDER_ID=$(echo $ORDER)
echo "Order ID: $ORDER_ID"

echo -e "\n=== 5. Process Payment ==="
PAYMENT=$(curl -s -X POST http://localhost:8085/api/v1/payments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "amount": 1999.98,
    "paymentMethod": "CREDIT_CARD",
    "orderId": '$ORDER_ID',
    "orderReference": "ORD-'$(date +%s)'",
    "customer": {
      "id": "'$CUSTOMER_ID'",
      "firstname": "Alice",
      "lastname": "Smith",
      "email": "alice@example.com"
    }
  }')

PAYMENT_ID=$(echo $PAYMENT)
echo "Payment ID: $PAYMENT_ID"

echo -e "\n=== Workflow Complete ==="
```

Save as `workflow.sh` and run:
```bash
chmod +x workflow.sh
./workflow.sh
```

---

## 🔍 Debugging Tips

### Print Response with Pretty Formatting
```bash
curl -s http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN" | jq '.'
```

### Save Response to File
```bash
curl http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN" \
  > customers.json

cat customers.json
```

### Check Response Headers
```bash
curl -i http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN"
```

### Verbose Output (shows all details)
```bash
curl -v http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer $TOKEN"
```

### Test with Invalid Token
```bash
curl -X GET http://localhost:8082/api/v1/customers \
  -H "Authorization: Bearer invalid-token"

# Should return 401 Unauthorized
```

---

## ❌ Common Issues & Solutions

### Issue 1: Connection Refused
```
curl: (7) Failed to connect
```
**Solution**: 
- Check if service is running: `docker ps`
- Check correct port number
- Ensure firewall allows connection

### Issue 2: 401 Unauthorized
```json
{"status": 401, "error": "Unauthorized"}
```
**Solution**:
- Verify JWT token is valid
- Check token hasn't expired
- Ensure Authorization header format is correct: `Bearer <token>`

### Issue 3: 400 Bad Request
```json
{"status": 400, "error": "Bad Request", "message": "Customer firstname is required"}
```
**Solution**:
- Check all required fields are included
- Verify field types (strings, numbers, etc.)
- Validate email format
- Check numbers are positive

### Issue 4: 404 Not Found
```json
{"status": 404, "error": "Not Found"}
```
**Solution**:
- Verify resource ID exists
- Check endpoint path is correct
- Ensure you're using correct service port

### Issue 5: 500 Internal Server Error
```json
{"status": 500, "error": "Internal Server Error"}
```
**Solution**:
- Check service logs: `docker logs <container-name>`
- Verify database is running
- Ensure all dependencies are available

---

## 📊 API Response Formats

### Success Response (200 OK)
```json
{
  "id": "cust-123",
  "firstname": "John",
  "lastname": "Doe",
  "email": "john@example.com"
}
```

### List Response (200 OK)
```json
[
  {"id": "cust-1", "firstname": "John", ...},
  {"id": "cust-2", "firstname": "Jane", ...}
]
```

### Error Response (4xx or 5xx)
```json
{
  "timestamp": "2024-01-01T10:00:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": "Field validation error"
}
```

---

## 🔐 Security Best Practices

1. **Never expose tokens in version control**
   ```bash
   # Bad ❌
   TOKEN="abc123xyz"
   
   # Good ✅
   # Store in environment variable
   export TOKEN="abc123xyz"
   ```

2. **Use HTTPS in production** (not HTTP)
   ```bash
   # Production
   curl https://api.example.com/api/v1/customers
   
   # Local testing only
   curl http://localhost:8082/api/v1/customers
   ```

3. **Don't log sensitive data**
   ```bash
   # Bad ❌
   echo "Login with password: $PASSWORD"
   
   # Good ✅
   echo "Login successful"
   ```

4. **Validate inputs client-side**
   ```javascript
   // Before sending to API
   if (!email.includes('@')) {
     console.error('Invalid email format');
     return;
   }
   ```

---

## 📈 Performance Tips

### Use Pagination (When Implemented)
```bash
curl -X GET "http://localhost:8082/api/v1/customers?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"
```

### Use Filtering (When Implemented)
```bash
curl -X GET "http://localhost:8082/api/v1/customers?city=NewYork" \
  -H "Authorization: Bearer $TOKEN"
```

### Batch Operations
```bash
# Instead of multiple requests
# Create many products at once
curl -X POST http://localhost:8083/api/v1/products/batch \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '[...products...]'
```

---

## 🧪 Testing Checklist

Before going to production:

- [ ] All endpoints return correct status codes
- [ ] Authentication works (201, 200 responses)
- [ ] Error handling works (400, 401, 404 responses)
- [ ] Required fields are validated
- [ ] Email format is validated
- [ ] Numbers are positive where required
- [ ] Database operations work (create, read, update, delete)
- [ ] Inter-service calls work
- [ ] Payment processing works
- [ ] Error messages are helpful

---

## 🚢 Deployment Checklist

- [ ] Use HTTPS for all endpoints
- [ ] Set up proper logging
- [ ] Configure error tracking
- [ ] Set up monitoring/alerts
- [ ] Use secure password hashing
- [ ] Implement rate limiting
- [ ] Add request validation
- [ ] Set up backup strategy
- [ ] Configure database security
- [ ] Test failover scenarios

---

## 📞 Getting Help

### Documentation References
- **Full API Docs**: See `API_DOCUMENTATION.md`
- **Endpoint Summary**: See `API_ENDPOINTS_SUMMARY.md`
- **cURL Examples**: See `CURL_COMMANDS.md`
- **Postman Import**: Use `postman_collection.json`

### Additional Resources
- Service README files in `services/` directory
- Check `docker-compose.yml` for service configuration
- Review source code in `services/*/src/main/java/`

### Common Commands
```bash
# View service logs
docker logs <service-name> -f

# List running services
docker-compose ps

# Restart a service
docker-compose restart <service-name>

# View database
docker exec -it postgres psql -U user -d ecommerce

# Check service health
curl http://localhost:<port>/health
```

---

## 📝 Sample Projects

### 1. Simple CLI Tool
Create a Python script to interact with APIs:
```python
import requests
import json

BASE_URL = "http://localhost:8082"
TOKEN = "your-token"

response = requests.get(
    f"{BASE_URL}/api/v1/customers",
    headers={"Authorization": f"Bearer {TOKEN}"}
)
print(json.dumps(response.json(), indent=2))
```

### 2. Frontend Integration
```javascript
const token = localStorage.getItem('authToken');

fetch('http://localhost:8082/api/v1/customers', {
  method: 'GET',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  }
})
.then(response => response.json())
.then(data => console.log(data));
```

### 3. Integration Tests
```java
@Test
public void testCreateCustomer() {
    CustomerRequest request = new CustomerRequest(
        null, "John", "Doe", "john@example.com", address);
    
    ResponseEntity<String> response = restTemplate.postForEntity(
        "http://localhost:8082/api/v1/customers",
        new HttpEntity<>(request, headers),
        String.class);
    
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
}
```

---

## 🎓 Learning Path

1. **Beginner**
   - Read this guide
   - Try Postman collection
   - Run complete workflow example

2. **Intermediate**
   - Use cURL commands
   - Understand error codes
   - Test error scenarios

3. **Advanced**
   - Write integration tests
   - Implement client SDK
   - Set up CI/CD pipeline

---

## 📞 Support

For issues or questions:
1. Check documentation files
2. Review error messages
3. Check service logs
4. Verify all services are running
5. Consult source code

---

## 🎉 Next Steps

1. ✅ Start services
2. ✅ Register user
3. ✅ Get JWT token
4. ✅ Test endpoints
5. ✅ Build integration

**Now you're ready to start building!**

---

**Last Updated**: 2024
**API Version**: 1.0

