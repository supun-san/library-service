# 📚 Library Management System

A RESTful API to manage a simple library system. Built using Java 17 and Spring Boot, this application allows registration of books and borrowers, borrowing and returning of books, and listing all books.

---

## 🚀 Features

* Register new books and borrowers
* Borrow and return books
* Enforce unique borrowing (one book cannot be borrowed by multiple users at the same time)
* Support for multiple copies of books with the same ISBN
* Validation and error handling
* Environment-based configuration
* Dockerized for easy deployment

---

## 🛠️ Technologies Used

* Java 17
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Docker
* Maven

---

## 🛆 Environment Variables

Create a `.env` file in the root directory:

```
DB_USER=your_db_user
DB_PASS=your_db_password
DB_NAME=your_db_name
```

---

## 🐳 Docker Commands

### 🏗️ Build Docker Image

```bash
docker build -t library-system .
```

### ▶️ Run Docker Container

```bash
docker run -d -p 8080:8080 --name library-app \
  -e DB_USER=your_db_user \
  -e DB_PASS=your_db_password \
  -e DB_NAME=your_db_name \
  library-system
```

Or using `.env` file:

```bash
docker run -d -p 8080:8080 --name library-app \
  --env-file .env \
  library-system
```

### 🧹 Stop and Remove Container

```bash
docker stop library-app
docker rm library-app
```

---

## 🐳 Docker Compose

```yaml
version: '3.8'

services:
  db:
    image: postgres
    environment:
      POSTGRES_DB: ${DB_NAME}
      POSTGRES_USER: ${DB_USER}
      POSTGRES_PASSWORD: ${DB_PASS}
    ports:
      - "5432:5432"
    volumes:
      - postgres-data:/var/lib/postgresql/data

  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - db
    env_file:
      - .env

volumes:
  postgres-data:
```

Start containers:

```bash
docker-compose up --build
```

---

## 📬 API Endpoints

### Book

* `POST /api/v1/books` - Register a new book
* `GET /api/v1/books` - List all books

### Borrower

* `POST /api/v1/borrowers` - Register a new borrower

### Library

* `POST /api/v1/library/borrow/{borrowerId}/{bookId}` - Borrow a book
* `POST /api/v1/library/return/{borrowerId}/{bookId}` - Return a book

---

## 📘 API Documentation with Swagger UI

This project includes **Swagger UI** for interactive API documentation.

### How to Access Swagger UI

Once the application is running (locally or via Docker), open your web browser and go to:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

This page provides a user-friendly interface to:

- View all available API endpoints  
- See request and response formats, including data models   
- Read endpoint descriptions and details

---

## 🧪 Unit Testing

This project contains comprehensive unit tests focused on the service layer to verify core business logic.

### Testing Frameworks and Tools

- **JUnit 5** for writing and running unit tests  
- **Mockito** for mocking dependencies and isolating units under test  
- **Spring Boot Test** support for context loading when necessary  

### What is Tested

- Successful borrowing and returning of books  
- Proper exception handling when borrower or book is missing  
- Validation that books are available before borrowing  

### Running Tests

Run all tests with Maven:

```bash
mvn test
```

---

## 📝 Assumptions

* ISBN uniquely identifies book type (not copy)
* Same ISBN => same title and author
* Book copies have unique internal IDs
* One book ID can only be borrowed by one borrower at a time

---

## 📖 License

This project is licensed under the MIT License.

---

## 👨‍💼 Author

Developed by Supunsan
