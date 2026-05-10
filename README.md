📏 Quantity Measurement Microservices Application
Spring Boot 3.x + Spring Cloud | Java 17
🚀 Overview

This project is a Spring Cloud based microservices application designed to perform various quantity measurement operations such as:

Quantity Conversion
Quantity Comparison
Arithmetic Operations on Quantities
Operation History Tracking
Error History Management

The application was initially developed as a monolithic Spring Boot application and later migrated into a modular microservices architecture using Spring Cloud technologies.

🏗️ System Architecture

The system follows a microservices-based architecture, where each service is independently deployable and registered through a centralized discovery server.

Architecture Flow
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
⚙️ Core Infrastructure Services
Component	Port	Technology	Responsibility
API Gateway	8080	Spring Cloud Gateway	Centralized routing & request forwarding
Eureka Server	8761	Netflix Eureka	Service discovery & registration
🧩 Microservices Overview
#	Service	Port	Database	Responsibility
1	Measurement Service	8081	H2	Quantity operations, JWT security, history tracking
2	User Service	8082	In-Memory	User-related APIs
📌 Supported Measurement Types

The system currently supports:

Weight Measurement
Length Measurement
Volume Measurement
Temperature Measurement
🔐 Security Features
Spring Security
JWT Authentication
Protected REST APIs
User Registration & Login
BCrypt Password Encoding
Role-Based Authorization
🛠️ Technologies Used
Backend
Java 17
Spring Boot 3.x
Spring Cloud
Spring Security
Spring Cloud Gateway
Netflix Eureka
Spring Data JPA
Hibernate
Database
H2 Database
Documentation
Swagger / OpenAPI
Build Tool
Maven
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
├── pom.xml
│
└── README.md
📖 API Documentation
Swagger UI
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
📌 Quantity Measurement APIs
Method	Endpoint	Description
POST	/api/v1/quantities/convert	Convert quantities
POST	/api/v1/quantities/compare	Compare quantities
POST	/api/v1/quantities/add	Add quantities
GET	/api/v1/quantities/history/operation/{operation}	Get operation history
GET	/api/v1/quantities/history/errored	Get errored operations
GET	/api/v1/quantities/count/{operation}	Get operation count
✅ Implemented Use Cases
UC	Description	Status
UC17	REST APIs + JPA + Swagger + Validation	✅
UC18	Spring Security + JWT Authentication	✅
UC19	Spring Cloud Microservices Migration	✅
🎯 Key Features
Microservices-based architecture
Multi-module Maven project structure
Service discovery using Eureka
Centralized API routing
JWT-secured APIs
Swagger documentation
Operation history tracking
Modular and scalable backend design
📌 Future Enhancements
Spring Cloud Config Server
Dockerization
Kubernetes Deployment
MySQL/PostgreSQL Integration
OpenFeign Communication
Kafka/RabbitMQ Messaging
Distributed Tracing
CI/CD Integration
👩‍💻 Author

Jayanthi M
Backend Developer | Java | Spring Boot | Microservices | AI & ML Enthusiast

⭐ Final Note

This project demonstrates the migration of a traditional Spring Boot monolithic application into a scalable Spring Cloud microservices architecture using industry-standard backend technologies and best practices.
