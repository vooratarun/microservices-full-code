# 📚 E-Commerce Microservices API Documentation - Complete Index

## Welcome! 👋

This is your central hub for all API documentation. Choose the format that works best for you.

---

## 📖 Documentation by Format

### 1️⃣ **Getting Started** (START HERE!)
📄 **File**: `GETTING_STARTED.md`
- 5-minute quick start
- Step-by-step guide for beginners
- Common issues & solutions
- Multiple tool options (Postman, cURL, REST Client)
- Complete workflow examples

👉 **Best for**: New developers, first-time users

---

### 2️⃣ **Detailed API Reference**
📄 **File**: `API_DOCUMENTATION.md`
- Complete endpoint descriptions
- Request/response examples
- HTTP status codes
- Data models
- Microservices architecture
- Error handling guide

👉 **Best for**: Developers who need detailed information

---

### 3️⃣ **Quick Reference Tables**
📄 **File**: `API_ENDPOINTS_SUMMARY.md`
- All endpoints in table format
- Quick field lookup
- Validation rules
- Status code reference
- Required fields checklist

👉 **Best for**: Quick lookup, copy-paste templates

---

### 4️⃣ **cURL Command Examples**
📄 **File**: `CURL_COMMANDS.md`
- Individual cURL commands for each endpoint
- Complete workflow shell script
- Useful cURL tips and tricks
- Error handling examples
- Service URLs reference

👉 **Best for**: Terminal testing, shell scripting, CI/CD

---

### 5️⃣ **OpenAPI/Swagger Specification**
📄 **File**: `openapi.yaml`
- OpenAPI 3.0 format
- Machine-readable specification
- Can be imported into Swagger UI, ReDoc, code generators

👉 **Best for**: Swagger UI, code generation, API documentation tools

**How to use**:
```bash
# View with Swagger UI (Docker)
docker run -p 8888:8080 -e SWAGGER_JSON=/api/openapi.yaml \
  -v $(pwd)/openapi.yaml:/api/openapi.yaml \
  swaggerapi/swagger-ui

# Then visit http://localhost:8888
```

---

### 6️⃣ **Postman Collection**
📄 **File**: `postman_collection.json`
- Pre-configured requests for all endpoints
- Environment variables for easy reuse
- Example request bodies
- Can be imported directly into Postman

👉 **Best for**: Interactive testing with Postman, team collaboration

**How to import**:
1. Open Postman
2. Click Import → File
3. Select `postman_collection.json`
4. Set `token` variable with your JWT

---

### 7️⃣ **Documentation Overview**
📄 **File**: `API_DOCS_README.md`
- Overview of all documentation files
- Microservices architecture diagram
- Service responsibilities
- Common workflows
- Tools & resources

👉 **Best for**: Understanding the bigger picture

---

## 🎯 Choose Your Path

### 👤 I'm a Beginner
1. Start with: `GETTING_STARTED.md`
2. Then try: `postman_collection.json` (with Postman desktop app)
3. Reference: `API_DOCUMENTATION.md` (as needed)

### 👨‍💻 I'm a Developer
1. Start with: `API_ENDPOINTS_SUMMARY.md`
2. Use: `CURL_COMMANDS.md` (for testing)
3. Reference: `API_DOCUMENTATION.md` (detailed info)
4. Import: `openapi.yaml` (into tools)

### 🔧 I'm DevOps/Backend
1. Start with: `CURL_COMMANDS.md`
2. Use: Complete workflow script from `GETTING_STARTED.md`
3. Reference: `openapi.yaml` (for automation)
4. Import: `postman_collection.json` (for team)

### 🎨 I'm Frontend Developer
1. Start with: `GETTING_STARTED.md`
2. Use: `API_DOCUMENTATION.md` (full reference)
3. Import: `postman_collection.json` (to test)
4. Reference: `API_ENDPOINTS_SUMMARY.md` (field validation)

### 🤖 I'm Building Integration Tests
1. Start with: `API_DOCUMENTATION.md`
2. Use: `API_ENDPOINTS_SUMMARY.md` (field requirements)
3. Reference: `CURL_COMMANDS.md` (for examples)
4. Import: `openapi.yaml` (for test generation)

---

## 🚀 Quick Links

### Authentication
- Register: `API_DOCUMENTATION.md` → Section 1.1
- Login: `API_DOCUMENTATION.md` → Section 1.2
- Logout: `API_DOCUMENTATION.md` → Section 1.3
- cURL: `CURL_COMMANDS.md` → Authentication Endpoints

### Customers
- Create: `API_DOCUMENTATION.md` → Section 2.1
- List: `API_DOCUMENTATION.md` → Section 2.2
- Get: `API_DOCUMENTATION.md` → Section 2.3
- Update: `API_DOCUMENTATION.md` → Section 2.5
- Delete: `API_DOCUMENTATION.md` → Section 2.6

### Products
- Create: `API_DOCUMENTATION.md` → Section 3.1
- List: `API_DOCUMENTATION.md` → Section 3.2
- Get: `API_DOCUMENTATION.md` → Section 3.3
- Purchase: `API_DOCUMENTATION.md` → Section 3.4

### Orders
- Create: `API_DOCUMENTATION.md` → Section 4.1
- List: `API_DOCUMENTATION.md` → Section 4.2
- Get: `API_DOCUMENTATION.md` → Section 4.3
- Order Lines: `API_DOCUMENTATION.md` → Section 4.4

### Payments
- Process: `API_DOCUMENTATION.md` → Section 5.1

---

## 📊 Quick Reference

### Services & Ports
```
Auth Service:        8081
Customer Service:    8082
Product Service:     8083
Order Service:       8084
Payment Service:     8085
Notification Service: 8086
API Gateway:         8080
```

### HTTP Methods
- `POST` - Create resource
- `GET` - Retrieve resource
- `PUT` - Update resource
- `DELETE` - Delete resource

### Status Codes
- `200` - OK
- `201` - Created
- `202` - Accepted
- `400` - Bad Request
- `401` - Unauthorized
- `404` - Not Found
- `500` - Server Error

### Required Headers
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>  (for authenticated endpoints)
```

---

## 🛠️ Tools Needed

### Testing Tools
- **Postman**: Desktop app or web version
  - Download: https://www.postman.com/downloads/
  - Use: `postman_collection.json`

- **cURL**: Command-line tool (usually pre-installed)
  - Use: `CURL_COMMANDS.md`

- **VS Code REST Client**: VS Code extension
  - Install: "REST Client" by Huachao Mao

### Documentation Tools
- **Swagger UI**: View OpenAPI specs
  - Docker image: `swaggerapi/swagger-ui`
  - Use with: `openapi.yaml`

- **ReDoc**: Alternative API documentation viewer
  - Docker image: `redocly/redoc`
  - Use with: `openapi.yaml`

---

## 💾 File Locations

All files are in the root directory:
```
/home/tarv/Desktop/springboot/springboot/microservices-full-code/
├── GETTING_STARTED.md                    ← Start here
├── API_DOCUMENTATION.md                  ← Full reference
├── API_ENDPOINTS_SUMMARY.md              ← Quick lookup
├── API_DOCS_README.md                    ← Overview
├── CURL_COMMANDS.md                      ← cURL examples
├── openapi.yaml                          ← OpenAPI spec
├── postman_collection.json               ← Postman import
└── API_DOCUMENTATION_INDEX.md            ← This file
```

---

## 🔒 Authentication

All endpoints except registration and login require a JWT token:

```bash
Authorization: Bearer <your-jwt-token>
```

### Getting a Token
1. POST to `/api/v1/auth/register` (for new users)
2. POST to `/api/v1/auth/login` (for existing users)
3. Copy the `token` from response
4. Include in Authorization header for all other requests

---

## 🔄 Complete Workflow

```
1. Register → Get token
      ↓
2. Create customer → Get customer ID
      ↓
3. Create product → Get product ID
      ↓
4. Create order → Get order ID
      ↓
5. Process payment → Get payment ID
```

See `GETTING_STARTED.md` for full workflow script.

---

## ❓ Frequently Asked Questions

### Q: Where do I start?
A: Read `GETTING_STARTED.md` first (5-10 minutes)

### Q: How do I test endpoints?
A: Three options:
- Use Postman: Import `postman_collection.json`
- Use cURL: Follow `CURL_COMMANDS.md`
- Use REST Client: Create `.http` file in VS Code

### Q: I'm getting 401 Unauthorized?
A: Your JWT token might be invalid or expired. Login again.

### Q: I'm getting 400 Bad Request?
A: Check required fields in `API_ENDPOINTS_SUMMARY.md`

### Q: How do I view the API in Swagger UI?
A: Follow instructions in "OpenAPI/Swagger Specification" section above

### Q: Can I use this in production?
A: Yes, but review security considerations in `API_DOCUMENTATION.md`

### Q: Do you have client SDKs?
A: Not yet, but you can generate them from `openapi.yaml`

---

## 📞 Support Resources

1. **Full Documentation**: `API_DOCUMENTATION.md`
2. **Quick Lookup**: `API_ENDPOINTS_SUMMARY.md`
3. **Command Examples**: `CURL_COMMANDS.md`
4. **Getting Started**: `GETTING_STARTED.md`
5. **Service READMEs**: `services/*/README.md`

---

## 🎓 Learning Resources

### Beginner
- REST API basics: https://restfulapi.net/
- HTTP methods: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
- JSON: https://www.json.org/

### Intermediate
- OpenAPI/Swagger: https://swagger.io/
- JWT authentication: https://jwt.io/
- Spring Boot: https://spring.io/projects/spring-boot

### Advanced
- Microservices architecture: https://microservices.io/
- Service discovery: https://spring.io/projects/spring-cloud-netflix
- API gateway patterns: https://microservices.io/patterns/apigateway.html

---

## 📋 Checklist for New Users

- [ ] Read `GETTING_STARTED.md`
- [ ] Start all services
- [ ] Register a user account
- [ ] Get JWT token
- [ ] Test one endpoint
- [ ] Try complete workflow
- [ ] Read full documentation
- [ ] Set up your testing tool (Postman/cURL)
- [ ] Review error handling
- [ ] Start building integration

---

## 🎉 What's Next?

1. **Immediate**: Read `GETTING_STARTED.md` (15 minutes)
2. **Short-term**: Set up testing tool and try endpoints
3. **Medium-term**: Build integration with your application
4. **Long-term**: Contribute improvements to API documentation

---

## 📝 Documentation Metadata

- **Created**: 2024
- **API Version**: 1.0
- **OpenAPI Version**: 3.0.0
- **Spring Boot Version**: 3.0.2
- **Last Updated**: 2024

---

## 🤝 Contributing

Found issues or have suggestions? Feel free to:
1. Check existing documentation
2. Review source code in `services/*/src/main/java/`
3. Submit feedback or improvements

---

**Ready to get started?** → Read `GETTING_STARTED.md`

**Need quick reference?** → Read `API_ENDPOINTS_SUMMARY.md`

**Want full details?** → Read `API_DOCUMENTATION.md`

---

**Happy coding! 🚀**

