# Inventory Management System - Backend

This is the backend application for the Inventory Management System, built with Spring Boot and Java.

## Project Structure

```
back/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/breakable/toy/
│   │   │       ├── config/           # Configuration classes
│   │   │       ├── controller/       # REST API controllers
│   │   │       ├── exception/        # Custom exception classes
│   │   │       ├── model/            # Domain model entities
│   │   │       ├── service/          # Business logic services
│   │   │       └── Backend.java      # Main application class
│   │   └── resources/
│   │       └── application.properties # Application configuration
│   └── test/                         # Unit and integration tests
├── .mvn/                             # Maven wrapper configuration
├── target/                           # Compiled output (generated)
├── mvnw                              # Maven wrapper script (Unix)
├── mvnw.cmd                          # Maven wrapper script (Windows)
└── pom.xml                           # Maven project configuration
```

## Features

- **Product Management**
  - Create, read, update, and delete products
  - Set products as out of stock
  - Update product stock levels

- **Category Management**
  - Retrieve all available product categories
  - Products are organized by categories

- **Statistics Calculation**
  - Calculate inventory statistics by category:
    - Total products in stock
    - Total value of inventory
    - Average price of products in stock
  - Real-time statistics updates when product inventory changes

- **RESTful API**
  - JSON-based API for frontend integration
  - Filtering capabilities for product queries
  - Proper error handling and status codes

## Architecture

The application follows a standard layered architecture:

1. **Controller Layer**: Handles HTTP requests/responses and defines the API endpoints
2. **Service Layer**: Contains business logic and coordinates operations
3. **Repository Layer**: Manages data persistence (in-memory for this application)
4. **Model Layer**: Defines the domain entities and data structures

## API Endpoints

- **Products**
  - `GET /products`: Retrieve all products (with optional filters)
  - `GET /products/{id}`: Retrieve a specific product
  - `POST /products`: Create a new product
  - `PUT /products/{id}`: Update an existing product
  - `DELETE /products/{id}`: Delete a product
  - `POST /products/{id}/outOfStock`: Set a product as out of stock

- **Categories**
  - `GET /categories`: Retrieve all product categories

- **Statistics**
  - `GET /statistics`: Retrieve inventory statistics by category

## Getting Started

1. Ensure you have Java 23 installed:
   ```
   java -version
   ```

2. Run the application using Maven:
   ```
   ./mvnw spring-boot:run
   ```

3. The API will be available at:
   ```
   http://localhost:8080
   ```

## Testing

Run the test suite using Maven:
```
./mvnw test
```

## Building

Build an executable JAR file:
```
./mvnw clean package
```

The JAR will be available in the `target/` directory.

## Frontend Integration

This backend application provides the API for the Next.js frontend. Both applications can be started together using the provided `run.sh` script in the root directory. 