# Order Management System

A RESTful backend application built using **Java 17 and Spring Boot** to manage users, products, orders, and order items. The application implements secure authentication using JWT and role-based authorization to control access to protected resources.

## Technologies Used

- **Language:** Java 17
- **Framework:** Spring Boot, Spring MVC
- **ORM:** Spring Data JPA, Hibernate
- **Security:** Spring Security, JWT
- **Database:** PostgreSQL
- **Build Tool:** Maven
- **API Testing:** Postman

## Key Features

### 1. Authentication and Security
- User registration and login.
- JWT generation after successful authentication.
- Stateless authentication using JWT.
- Password hashing using BCrypt.
- Custom `UserDetails` implementation.
- Role-based authorization using Spring Security.
- Custom authentication entry point and access-denied handler.

### 2. User Management
- User management REST APIs.
- Role-based access permissions.
- Secure handling of user authentication details.

### 3. Product Management
- REST APIs for product management.
- Product-related operations protected through role-based authorization.
- Product availability and stock management.

### 4. Order Management
- Create and manage customer orders.
- Associate orders with customers.
- Manage multiple order items within an order.
- Validate product availability and stock before processing orders.
- Handle insufficient stock scenarios.

### 5. Order Item Management
- Create order items associated with orders and products.
- Retrieve, update, and delete order items.
- Validate order item request data.

### 6. Exception Handling and Validation
- Centralized exception handling using `@RestControllerAdvice`.
- Custom exceptions for application-specific errors.
- Request validation using Jakarta Bean Validation.
- Consistent API response structures.

### 7. Optimistic Locking
- Uses optimistic locking to help prevent conflicting concurrent updates to shared data.

## User Roles

The application supports three roles:

| Role | Responsibility |
|---|---|
| ADMIN | Administrative operations |
| MANAGER | Management-related operations |
| CUSTOMER | Customer-related operations |

Access to protected resources is controlled through Spring Security role-based authorization.

## Authentication Flow

1. A user registers or logs in using the authentication APIs.
2. Spring Security authenticates the user credentials.
3. The password is verified using BCrypt.
4. A JWT token is generated after successful authentication.
5. The client sends the token with subsequent requests.
6. A custom JWT authentication filter validates the token and establishes the user's authentication context.
7. Authorization rules determine whether the user can access the requested resource.

## Order Workflow

The application's main business workflow is:

1. User registration and authentication.
2. Product retrieval.
3. Customer places an order.
4. Order items are associated with the order and selected products.
5. Product availability and stock are checked.
6. Order-related operations are processed.

## Database

PostgreSQL is used as the relational database.

Spring Data JPA and Hibernate handle database operations and object-relational mapping.

The application contains entities representing:

- User
- Role
- Product
- Order
- OrderItem

These entities represent the core relationships required for user, product, and order management.

## Project Structure

```text
OrderManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ty/OrderMangementSystem/
│   │   │       ├── controller/
│   │   │       ├── service/
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       ├── dto/
│   │   │       ├── security/
│   │   │       ├── jwt/
│   │   │       ├── Exception/
│   │   │       └── util/
│   │   └── resources/
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Configuration

Configure the following environment variables before running the application:

|   Variable    |            Description            |
|---------------|-----------------------------------|
| `DB_URL`      |    PostgreSQL database URL        |
| `DB_USERNAME` |    Database username              |
| `DB_PASSWORD` |     Database password             |
| `JWT_SECRET`  |   Secret key used for JWT signing |

Do not commit database credentials or JWT secrets to the repository.

## How to Run

1. Clone the repository.
2. Create a PostgreSQL database.
3. Configure the required environment variables.
4. Import the project into Eclipse or another Java IDE.
5. Build and run the Spring Boot application.
6. Use Postman to test the REST APIs.

## API Testing

Postman can be used to test the application's REST APIs, including authentication, user management, product management, order management, and order item operations.

## Author

**Java Backend Developer**
