# Inventory Management System

A full-stack inventory management application built with Spring Boot (backend) and Next.js (frontend).

## Project Overview

This application helps businesses manage their inventory by tracking products, monitoring stock levels, and providing statistics on inventory value and composition. It features a responsive UI with real-time updates and visual indicators for stock levels.

## Features

- **Product Management**
  - Create, read, update, and delete products
  - Track product details (name, category, price, quantity, expiration date)
  - Set products as out of stock
  - Visual indicators for stock levels and expired products

- **Inventory Analysis**
  - Filter and search products by name, category, and availability
  - View statistics by category:
    - Total products in stock
    - Total value of inventory
    - Average price of products
  - Real-time statistics updates when inventory changes

- **User Interface**
  - Responsive Material UI components
  - Data grid with sorting and filtering capabilities
  - Visual indicators for low stock (yellow for 5-10 items, red for <5 items)
  - Visual indicators for expired products

## Project Structure

The project is organized as a monorepo with two main directories:

```
/
├── back/                  # Spring Boot backend
│   ├── src/               # Source code
│   └── pom.xml            # Maven configuration
├── front/                 # Next.js frontend
│   ├── app/               # Next.js app components
│   ├── components/        # React components
│   ├── hooks/             # Custom React hooks
│   ├── utils/             # Utility functions and API
│   └── package.json       # npm configuration
└── run.sh                 # Script to run both applications
```

For more detailed information about each part of the application:
- [Backend Documentation](back/README.md)
- [Frontend Documentation](front/README.md)

## Prerequisites

- **Java**: Version 23 or higher
  ```
  java -version
  ```

- **Node.js**: Version 18 or higher
  ```
  node -v
  ```

- **npm**: Version 9 or higher
  ```
  npm -v
  ```

## Installation

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/inventory-management.git
   cd inventory-management
   ```

2. Install backend dependencies:
   ```
   cd back
   ./mvnw clean install
   cd ..
   ```

3. Install frontend dependencies:
   ```
   cd front
   npm install
   cd ..
   ```

## Running the Application

### Using the run script (recommended)

The easiest way to run both applications simultaneously is using the provided script:

```
chmod +x run.sh  # Make script executable (first time only)
./run.sh
```

This will start both the backend and frontend applications. Press `Ctrl+C` to stop both applications.

### Running separately

**Backend:**
```
cd back
./mvnw spring-boot:run
```
The backend API will be available at http://localhost:8080

**Frontend:**
```
cd front
npm run dev
```
The frontend will be available at http://localhost:3000

## Development

### Backend Development

For backend development, navigate to the `back` directory and use Maven for building, testing, and running the application.

```
cd back
./mvnw spring-boot:run
```

### Frontend Development

For frontend development, navigate to the `front` directory and use npm commands.

```
cd front
npm run dev
```

## Building for Production

### Building the Backend

```
cd back
./mvnw clean package
```

The executable JAR will be available in the `back/target/` directory.

### Building the Frontend

```
cd front
npm run build
```

The built files will be available in the `front/.next/` directory.

## License

[MIT License](LICENSE)

## Acknowledgments

- Spring Boot for the backend framework
- Next.js and React for the frontend framework
- Material UI for the component library 