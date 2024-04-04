# RentRead

RentRead is a RESTful API service developed using Spring Boot to manage an online book rental system. It allows users to register, login, browse books, rent books, return books, and manage user accounts. This document provides an overview of the application, its features, setup instructions, and other relevant information.

## Setup

To run this application locally, follow these steps:

1. Clone this repository:

   ```bash
   git clone https://github.com/harsh8999/RentRead.git
   ```

2. Build the application using Maven:
    ```bash
   mvnw clean install
   ```

3. Run the application:
    ```bash
    java -jar target/RentRead-0.0.1-SNAPSHOT.jar
    ```
    
## Api Dcoumentations Endpoint

- GET /swagger-ui/index.html - Api Documentations

## User Endpoints

- GET /api/v1/ - Welcome To RentRead.

## User Auth Endpoints
### Public Endpoints
- POST /api/v1/auth/signup - Register a new user (Role can be ADMIN, USER), Default Role: USER.
    ### Request Body
    ```json
    {
    	"firstName": "Harsh",
        "lastName": "Nayak",
        "email" : "harsh@gmail.com",
        "password" : "1234",
        "role" : "ADMIN"
    }
    ```
    ### Request Body
    ```json
    {
    	"firstName": "Harsh",
        "lastName": "Nayak",
        "email" : "harsh@gmail.com",
        "password" : "1234"
    }
    ```
- POST /api/v1/auth/login - Login User.
    ### Request Body
    ```json
    {
        "email" : "harsh@gmail.com",
        "password" : "1234"
    }
    ```

## User Endpoint
### Private ADMIN Endpoints 
### Authorization: Basic Authentication required with ADMIN credentials


- GET /api/v1/users - Get all the Users
- GET /api/v1/users/{user_id} - Get User By userId
- PUT /api/v1/users/{user_id}/update - Update a user by userId
    ### Request Body
    ```json
    {
    	"firstName" : "Sahil",
        "lastName" : "Kumar",
        "email" : "sahil@gmail.com",
        "password" : "121233"
    }
    ```

- PUT /api/v1/users/{user_id}/addRole - Update Role of a user 
    ### Request Body
    ```json
    {
    	"role": "USER"
    }
    ```
- DELETE /api/v1/users/{user_id} - Delete a User

## Book Endpoints
### Authorization: Basic Authentication required with user credentials

### Private Endpoints
- GET /api/v1/books - Get All Books
- GET /api/v1/books/{book_id} - Get Book By Id

### Private ADMIN Endpoints
- POST /api/v1/books - Add new Book 
    ### Request Body
    ```json
    {
        "title": "The Martian",
        "author": "Andy Weir",
        "genre": "SCIFI"
    }
    ```
- POST /api/v1/books/addAllBooks - Add List Of Books.
    ### Request Body
    ```json
    [
        {
            "title": "The Martian",
            "author": "Andy Weir",
            "genre": "SCIFI"
        },
        {
            
            "title": "Dune",
            "author": "Frank Herbert",
            "genre": "SCIFI"
        }
    ]
    ```
- PUT /api/v1/books/{book_id}/update - Update Book Details
    ### Request Body
    ```json
    {
        "title": "The Martian",
        "author": "Andy Weir",
        "genre": "SCIFI"
    }
    ```
- DELETE /api/v1/books/{book_id}/delete - Delete a Book

## Rent Book Endpoints
### Authorization: Basic Authentication required with user credentials

### Private Endpoints
- POST /api/v1/rental/books/{book_id}/rent - Rent a book.
- POST /api/v1/rental/books/{book_id}/return - Return a rented book.

### Private ADMIN Endpoints
- GET /api/v1/rental - Get All Rental Details for all users.



## Postman Collection
