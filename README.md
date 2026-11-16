# Order Management System

A backend application developed using Java and Spring Boot to manage users, products, and customer orders. It provides REST APIs with JWT-based authentication and role-based authorization to secure application resources.

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

## Key Features

### 1. Authentication and Authorization
- User registration and login
- JWT-based authentication
- Password encryption using BCrypt
- Stateless authentication
- Role-based authorization

### 2. User Management
- User management operations
- Role-based access permissions
- Custom UserDetails implementation

### 3. Product Management
- Product management REST APIs
- Role-based access control

### 4. Order Management
- Create and manage customer orders
- Manage order items
- Associate orders with customers

### 5. Exception Handling and Validation
- Custom exception handling
- Request validation
- Structured API responses

## User Roles

The application supports three roles:

| Role | Responsibility |
|---|---|
| ADMIN | Administrative operations |
| MANAGER | Management operations |
| CUSTOMER | Customer-related operations |

## Database

PostgreSQL is used as the relational database.

Spring Data JPA and Hibernate are used for database interaction and object-relational mapping.

## Project Structure

```text
OrderManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Configuration

Before running the application, configure the following environment variables:

- `DB_URL` – PostgreSQL database URL
- `DB_USERNAME` – Database username
- `DB_PASSWORD` – Database password
- `JWT_SECRET` – Secret key used for JWT signing

Do not commit actual passwords or secret keys to the repository.

## How to Run

1. Clone this repository.
2. Create a PostgreSQL database.
3. Configure the required environment variables.
4. Open the project in Eclipse or another Java IDE.
5. Run the Spring Boot application.

## API Testing

The REST APIs can be tested using Postman.

## Author

Java Backend Developer

