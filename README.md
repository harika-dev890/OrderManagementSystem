# Order Management System

I developed this Order Management System using Java and Spring Boot to manage users, products, orders, and order items.

The application provides REST APIs for different operations and uses Spring Security with JWT authentication and role-based authorization to secure the application.

## Technologies Used

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Maven
- Postman

## Features

### 1. Authentication and Authorization
- Implemented user registration and login functionality.
- Integrated JWT-based authentication.
- Used BCrypt password hashing for secure password storage.
- Implemented stateless authentication.
- Created a custom UserDetails implementation.
- Configured role-based authorization using Spring Security.
- Implemented custom authentication and access-denied handling.

### 2. User Management
- Implemented APIs to manage user details.
- Added role-based access permissions.
- Integrated user authentication with Spring Security.
- Used DTOs to transfer user data.

### 3. Product Management
- Implemented APIs for product management.
- Added role-based access control for product operations.
- Used DTOs to handle product requests and responses.

### 4. Order Management
- Implemented functionality to create and manage customer orders.
- Associated orders with customers.
- Managed order-related information.
- Implemented authorization checks to restrict access to customer orders.

### 5. Order Item Management
- Implemented APIs to create and manage order items.
- Associated order items with their respective orders and products.
- Added functionality to retrieve, update, and delete order items.

### 6. Exception Handling and Validation
- Implemented centralized exception handling using `@RestControllerAdvice`.
- Created custom exceptions for application-specific errors.
- Used Jakarta Bean Validation to validate request data.
- Implemented structured API responses.

### 7. Optimistic Locking
- Implemented optimistic locking to handle concurrent updates and help prevent conflicting modifications to shared data.

## User Roles

I implemented three roles in the application:

| Role | Responsibility |
|---|---|
| ADMIN | Administrative operations |
| MANAGER | Management-related operations |
| CUSTOMER | Customer-related operations |

Access to application resources is controlled through Spring Security and role-based authorization.

## JWT Authentication Flow

1. A user registers or logs in using the authentication APIs.
2. Spring Security authenticates the user credentials.
3. The password is verified using BCrypt.
4. A JWT token is generated after successful authentication.
5. The client sends the JWT token with subsequent requests.
6. A custom JWT authentication filter validates the token.
7. Spring Security establishes the authenticated user's security context.
8. Access to protected resources is controlled through authorization rules.

## Database

I used PostgreSQL as the relational database.

Spring Data JPA and Hibernate are used for database operations and object-relational mapping.

The application contains the following main entities:

- User
- Role
- Product
- Order
- OrderItem

These entities represent the relationships required for user, product, and order management.

## Project Architecture

I followed a layered architecture to organize the application:

- **Controller Layer:** Handles incoming REST API requests.
- **Service Layer:** Contains business logic and application operations.
- **Repository Layer:** Handles database operations using Spring Data JPA.
- **Entity Layer:** Contains entity classes and their relationships.
- **DTO Layer:** Transfers request and response data.
- **Security Layer:** Handles authentication and authorization.
- **JWT Layer:** Handles JWT authentication and token validation.
- **Exception Layer:** Handles application-specific exceptions.
- **Utility Layer:** Contains common response structures and utility classes.

## How to Run the Application

1. Clone the repository.
2. Create a PostgreSQL database.
3. Configure the database connection and JWT secret.
4. Import the project into Eclipse or another Java IDE.
5. Build and run the Spring Boot application.
6. Use Postman to test the REST APIs.
7. Authenticate through the login API and use the generated JWT token to access protected endpoints.

## API Testing

I used Postman to test the REST APIs, including authentication, user management, product management, order management, and order item operations.

## Author

Java Backend Developer
