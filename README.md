Quantity Measurement Application

A Spring Boot + Spring Cloud based microservices application for performing quantity measurement operations such as conversion, comparison, arithmetic operations, and operation history tracking.

🚀 Features
Quantity Measurement Operations
Quantity Conversion
Quantity Comparison
Addition of Quantities
Multiple Measurement Types:
Weight
Length
Volume
Temperature
🔐 Security Features
Spring Security
JWT Authentication
Protected APIs
User Registration & Login
BCrypt Password Encoding
🏗️ Microservices Architecture

The application has been migrated from a monolithic Spring Boot application into a Spring Cloud microservices architecture.

Services
Eureka Server → Service Discovery
API Gateway → Centralized Routing
Measurement Service → Quantity APIs & Business Logic
User Service → User-related APIs
🛠️ Tech Stack
Backend
Java 17
Spring Boot
Spring Security
Spring Cloud
Spring Cloud Gateway
Eureka Discovery Server
Spring Data JPA
Hibernate
H2 Database
Maven
Documentation
Swagger / OpenAPI
Authentication
JWT (JSON Web Token)
📂 Project Structure
quantity-measurement-app/
│
├── measurement-service/
│
├── eureka-server/
│
├── api-gateway/
│
├── user-service/
│
└── pom.xml
⚙️ Microservices Ports
Service	Port
Eureka Server	8761
API Gateway	8080
Measurement Service	8081
User Service	8082
🔄 Architecture Flow
Client
   ↓
API Gateway :8080
   ↓
Measurement Service :8081
   ↓
JWT Security + JPA
   ↓
H2 Database

All services registered in Eureka :8761
📖 Swagger Documentation
Measurement Service Swagger
http://localhost:8081/swagger-ui/index.html
Eureka Dashboard
http://localhost:8761
🔑 Authentication APIs
Register User
POST /auth/register
Sample Request
{
  "name": "Jayanthi",
  "email": "jayanthi@gmail.com",
  "password": "Password123",
  "role": "ROLE_USER"
}
Login
POST /auth/login
Sample Request
{
  "email": "jayanthi@gmail.com",
  "password": "Password123"
}
📌 Quantity APIs
Convert Quantities
POST /api/v1/quantities/convert
Compare Quantities
POST /api/v1/quantities/compare
Add Quantities
POST /api/v1/quantities/add
Operation History
GET /api/v1/quantities/history/operation/{operation}
Errored Operations
GET /api/v1/quantities/history/errored
Operation Count
GET /api/v1/quantities/count/{operation}
✅ Implemented Use Cases
UC	Description	Status
UC17	REST APIs + JPA + Swagger + Validation	✅
UC18	Spring Security + JWT Authentication	✅
UC19	Spring Cloud Microservices Migration	✅
📌 Future Enhancements
Config Server
Dockerization
Kubernetes Deployment
MySQL/PostgreSQL
OpenFeign
Kafka / RabbitMQ
Distributed Tracing
CI/CD Integration
👩‍💻 Author

Jayanthi M
MCA - Generative AI Specialization
Spring Boot | Microservices | AI & ML Enthusiast
