# 🎬 Cinema Ticket Booking System with Spring Security

A RESTful web application for cinema ticket booking built with Spring Framework, Spring Security, Hibernate ORM, and MySQL. This application demonstrates enterprise-level architecture with comprehensive security features, custom validation, and exception handling.

## 📋 Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [API Endpoints](#api-endpoints)
- [Security Implementation](#security-implementation)
- [Custom Validation](#custom-validation)
- [Exception Handling](#exception-handling)
- [Database Schema](#database-schema)

## ✨ Features

- **User Authentication & Registration**: Secure user registration with BCrypt password encoding
- **Spring Security Integration**: In-memory authentication with HTTP Basic and Form login support
- **Cinema Hall Management**: Create and manage cinema halls
- **Movie Management**: Add and retrieve movies
- **Movie Session Management**: Schedule movie sessions with specific cinema halls and show times
- **Shopping Cart**: Add movie session tickets to cart
- **Order Management**: Complete orders and view order history
- **Custom Validation**: Email format validation and password matching validation
- **Global Exception Handling**: Centralized error handling with detailed JSON responses
- **DTO Pattern**: Clean separation between API layer and domain models

## 🛠 Technologies Used

### Core Framework
- **Spring Framework 5.3.10**
  - Spring Context
  - Spring ORM
  - Spring Web MVC

### Security
- **Spring Security 5.5.2**
  - Authentication and Authorization
  - BCrypt Password Encoder
  - In-Memory User Details Manager

### Persistence
- **Hibernate ORM 5.5.7**
- **MySQL 8.0.26**
- **Apache Commons DBCP2 2.8.0** (Connection Pooling)

### Validation
- **Hibernate Validator 6.1.7**
- **Custom Validators** (Email, Password)

### Other
- **Jackson 2.12.5** (JSON Processing)
- **Maven** (Build Tool)
- **Java 11**

## 🏗 Architecture

The application follows a layered architecture pattern:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (Controllers + DTOs + Mappers)       │
├─────────────────────────────────────────┤
│         Business Logic Layer            │
│            (Services)                   │
├─────────────────────────────────────────┤
│      Data Access Layer (DAOs)           │
├─────────────────────────────────────────┤
│            Database (MySQL)             │
└─────────────────────────────────────────┘
```

### Key Design Patterns
- **DAO Pattern**: Separation of data persistence logic
- **DTO Pattern**: Data Transfer Objects for API communication
- **Dependency Injection**: Spring IoC container manages beans
- **Repository Pattern**: Generic CRUD operations
- **Mapper Pattern**: Conversion between entities and DTOs

## 📦 Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL 8.0+
- Apache Tomcat 9+ (or any servlet container)

## 🚀 Installation and Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd jv-spring-security
```

### 2. Configure Database
Update the database credentials in `src/main/resources/db.properties`:
```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/cinema_shop_spring?serverTimezone=UTC
db.user=your_username
db.password=your_password

hibernate.show_sql=true
hibernate.hbm2ddl.auto=create-drop
hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

### 3. Create Database
```sql
CREATE DATABASE cinema_shop_spring;
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Deploy to Tomcat
Deploy the generated WAR file from `target/spring-security-1.0-SNAPSHOT.war` to your Tomcat server.

### 6. Access the Application
The application will be available at: `http://localhost:8080/spring-security-1.0-SNAPSHOT/`

## 🔌 API Endpoints

### Authentication
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| POST | `/register` | Register a new user | No |

**Example Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "repeatPassword": "password123"
}
```

### Movies
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| GET | `/movies` | Get all movies | Yes |
| POST | `/movies` | Add a new movie | Yes |

**Example Request Body:**
```json
{
  "title": "Inception",
  "description": "A mind-bending thriller"
}
```

### Cinema Halls
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| GET | `/cinema-halls` | Get all cinema halls | Yes |
| POST | `/cinema-halls` | Add a new cinema hall | Yes |

### Movie Sessions
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| GET | `/movie-sessions/available` | Get available movie sessions | Yes |
| POST | `/movie-sessions` | Create a movie session | Yes |
| PUT | `/movie-sessions/{id}` | Update a movie session | Yes |
| DELETE | `/movie-sessions/{id}` | Delete a movie session | Yes |

### Shopping Cart
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| GET | `/shopping-carts/by-user` | Get current user's cart | Yes |
| PUT | `/shopping-carts/movie-sessions?movieSessionId={id}` | Add ticket to cart | Yes |

### Orders
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| POST | `/orders/complete` | Complete order from cart | Yes |
| GET | `/orders` | Get order history | Yes |

### Users
| Method | Endpoint | Description | Authentication Required |
|--------|----------|-------------|------------------------|
| GET | `/users/by-email?email={email}` | Get user by email | Yes |

## 🔐 Security Implementation

### Spring Security Configuration
The application uses **Spring Security** with the following features:

#### Authentication
- **In-Memory Authentication**: Pre-configured users for testing
  - User: `bob@i.ua` / Password: `1234`
  - User: `alice@i.ua` / Password: `4321`
- **BCrypt Password Encoding**: All passwords are hashed using BCrypt
- **Multiple Authentication Methods**: 
  - Form-based login
  - HTTP Basic authentication

#### Authorization
- All endpoints require authentication except `/register`
- Uses Spring Security's `Authentication` object to retrieve current user information

#### Code Example:
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin().permitAll()
        .and()
        .httpBasic()
        .and()
        .csrf().disable();
}
```

### Password Management
- Passwords are encoded using `BCryptPasswordEncoder`
- Original passwords are never stored in the database
- Authentication is handled by Spring Security framework

## ✅ Custom Validation

### 1. Email Validation (`@Email`)
Custom annotation that validates email format using regex pattern.

**Implementation:**
```java
@Email
private String email;
```

**Pattern:** RFC 5322 compliant email validation

### 2. Password Matching Validation (`@Password`)
Class-level annotation that ensures `password` and `repeatPassword` fields match.

**Implementation:**
```java
@Password
public class UserRequestDto {
    private String password;
    private String repeatPassword;
}
```

### Standard Validations
The application also uses standard JSR-303/380 annotations:
- `@Valid`: Triggers validation on request bodies
- `@NonNull`: Ensures fields are not null
- `@Min`: Validates minimum values

## ⚠️ Exception Handling

### Custom Global Exception Handler
Centralized exception handling using `@ControllerAdvice`:

#### Validation Errors (400 Bad Request)
Returns detailed field-level validation errors:
```json
{
  "timestamp": "2025-10-31T10:30:00",
  "status": 400,
  "errors": [
    "email must be a valid email format",
    "password Error password mismatch"
  ]
}
```

#### Data Processing Errors (500 Internal Server Error)
Handles `DataProcessingException` with custom error response:
```json
{
  "timestamp": "2025-10-31T10:30:00",
  "error class": "DataProcessingException",
  "status": "INTERNAL_SERVER_ERROR",
  "errors": [
    "Error processing request"
  ]
}
```

## 🗄 Database Schema

### Main Entities

#### User
- `id` (Primary Key)
- `email` (Unique)
- `password` (BCrypt encoded)

#### Movie
- `id` (Primary Key)
- `title`
- `description`

#### CinemaHall
- `id` (Primary Key)
- `capacity`
- `description`

#### MovieSession
- `id` (Primary Key)
- `movie_id` (Foreign Key)
- `cinema_hall_id` (Foreign Key)
- `show_time`

#### ShoppingCart
- `id` (Primary Key)
- `user_id` (Foreign Key)
- Many-to-Many relationship with Tickets

#### Order
- `id` (Primary Key)
- `user_id` (Foreign Key)
- `order_time`
- Many-to-Many relationship with Tickets

#### Ticket
- `id` (Primary Key)
- `movie_session_id` (Foreign Key)

## 📝 Notes

- The application uses `hibernate.hbm2ddl.auto=create-drop`, which recreates the database schema on each startup. For production, change this to `validate` or `update`.
- CSRF protection is disabled for simplicity. Enable it for production environments.
- Current implementation uses in-memory authentication. For production, implement database-backed user authentication.
