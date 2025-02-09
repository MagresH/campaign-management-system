# Campaign Management System

## Introduction

The **Campaign Management System** is an application designed to manage advertising campaigns for products sold by various sellers. This system allows sellers to create, edit, and delete campaigns for their products and manage their products.

## Features

- **Creating Sellers and Their Accounts**: Allows the creation of seller profiles along with their associated accounts.

- **CRUD Product Management**: Provides full Create, Read, Update, Delete (CRUD) operations for managing products associated with sellers.

- **CRUD Campaign Management**: Enables sellers to create, update, delete, and view advertising campaigns for their products.

- **User Account Balance Management**: Manages the balance of the seller's account, ensuring funds are available for campaigns.

- **Keyword Search Handling**: Supports searching for campaign keywords to improve accessibility and organization.

- **Location List Sharing**: Offers the ability to share and manage lists of locations related to campaigns.

- **Form Validation**: Includes validation mechanisms to ensure that data entered into forms is correct and complete before processing.

## Documentation

The **Campaign Management System** provides comprehensive API documentation using Swagger. You can access the Swagger UI to explore and test the API endpoints.

### Swagger UI

The Swagger UI is available at the following URL: `/api/swagger-ui/index.html`

This interface allows you to view the available API endpoints, their parameters, and responses. You can also use it to make test requests directly from your browser.

## Backend Stack

The backend of the **Campaign Management System** is built using the following technologies:

- **Java 21**

- **Spring Boot 3.4.2**

- **Spring Data JPA**

- **H2 Database**

## Installation

To install and run the **Campaign Management System** backend, follow these steps:

1. **Clean the project:**

    ```bash
    mvn clean
    ```

2. **Build the project:**

    ```bash
    mvn install
    ```

3. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

These commands will clean, build, and run application, ensuring that all dependencies are correctly installed and the application is properly set up for development or production use.
