# 📋 Task Manager API

REST API for task management, built with **Spring Boot** as a portfolio project.

## 🚀 Tech Stack

| Technology       | Version | Purpose                  |
|-----------------|---------|--------------------------|
| Java            | 17      | Main language            |
| Spring Boot     | 3.2     | Web framework            |
| Spring Data JPA | 3.2     | Database access          |
| H2 Database     | -       | In-memory DB (dev/demo)  |
| Swagger/OpenAPI | 2.3     | Auto-generated docs      |
| Lombok          | -       | Boilerplate reduction    |
| JUnit 5 + Mockito | -     | Unit testing             |
| Maven           | 3.x     | Dependency management    |

## 🏗️ Architecture

The project follows standard layered architecture:

```
Controller  →  Service  →  Repository  →  Banco de Dados
    ↕              ↕
   DTO           Model
```

- **Controller**: receives HTTP requests, validates input, returns responses
- **Service**: contains business logic, converts between DTOs and entities
- **Repository**: database access layer (Spring Data JPA)
- **Model**: database entities
- **DTO**: data transfer objects (API input and output)
- **Exception**: centralized error handling

## 📦 How to Run

### Requirements
- Java 17+
- Maven 3.6+

### Steps
```bash
# Clone the project
git clone https://github.com/your-username/task-manager.git
cd task-manager

# Build and run
mvn spring-boot:run
```

Application runs at `http://localhost:8080`

## 📖 API Documentation

After starting the application, visit:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

## 🔗 Endpoints

| Method | Endpoint                              | Description              |
|--------|---------------------------------------|--------------------------|
| GET    | `/api/v1/tasks`                       | List all tasks           |
| GET    | `/api/v1/tasks?status=TODO`           | Filter by status         |
| GET    | `/api/v1/tasks?priority=HIGH`         | Filter by priority       |
| GET    | `/api/v1/tasks/{id}`                  | Get by ID                |
| GET    | `/api/v1/tasks/search?keyword=text`   | Search by keyword        |
| GET    | `/api/v1/tasks/summary`               | Statistical summary      |
| POST   | `/api/v1/tasks`                       | Create task              |
| PUT    | `/api/v1/tasks/{id}`                  | Full update              |
| PATCH  | `/api/v1/tasks/{id}/status?status=DONE` | Update status only     |
| DELETE | `/api/v1/tasks/{id}`                  | Delete task              |

## 📝 Request Examples

### Create a task
```http
POST /api/v1/tasks
Content-Type: application/json

{
  "title": "Study Spring Boot",
  "description": "Complete the REST API tutorial",
  "priority": "HIGH",
  "dueDate": "2024-12-31T23:59:00"
}
```

### Response
```json
{
  "id": 1,
  "title": "Study Spring Boot",
  "description": "Complete the REST API tutorial",
  "status": "TODO",
  "priority": "HIGH",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "dueDate": "2024-12-31T23:59:00"
}
```

## ✅ Tests
```bash
# Run all tests
mvn test
```

## 🎯 Best Practices Applied

- ✅ **Layered architecture** (Controller → Service → Repository)
- ✅ **DTOs** to decouple API from database
- ✅ **Input validation** with Bean Validation
- ✅ **Centralized error handling** with `@RestControllerAdvice`
- ✅ **Custom exceptions** (`TaskNotFoundException`)
- ✅ **API versioning** (`/api/v1/`)
- ✅ **Semantic HTTP status codes** (200, 201, 204, 404)
- ✅ **Auto-generated documentation** with Swagger
- ✅ **Unit tests** with JUnit 5 + Mockito
- ✅ **Logging** with SLF4J/Logback
- ✅ **DRY principle** (no code repetition)

## 🔮 Future Improvements

- [ ] Authentication with Spring Security + JWT
- [ ] Migrate to PostgreSQL for production
- [ ] Pagination for task listing
- [ ] Integration tests
- [ ] Deploy to Railway/Render

---
Developed as a portfolio project to demonstrate Backend development skills in Java.

---
Desenvolvido como projeto de portfólio para demonstrar habilidades com desenvolvimento Backend em Java.
