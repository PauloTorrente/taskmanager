# 📋 Task Manager API

API REST para gerenciamento de tarefas, desenvolvida com **Spring Boot** como projeto de portfólio.

## 🚀 Tecnologias

| Tecnologia | Versão | Propósito |
|-----------|--------|-----------|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2 | Framework web |
| Spring Data JPA | 3.2 | Acesso ao banco de dados |
| H2 Database | - | Banco em memória (dev/demo) |
| Swagger/OpenAPI | 2.3 | Documentação automática |
| Lombok | - | Redução de código boilerplate |
| JUnit 5 + Mockito | - | Testes unitários |
| Maven | 3.x | Gerenciamento de dependências |

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas padrão do mercado:

```
Controller  →  Service  →  Repository  →  Banco de Dados
    ↕              ↕
   DTO           Model
```

- **Controller**: recebe requisições HTTP, valida entrada, retorna respostas
- **Service**: contém regras de negócio, faz conversão entre DTOs e entidades
- **Repository**: acesso ao banco de dados (Spring Data JPA)
- **Model**: entidades do banco de dados
- **DTO**: objetos de transferência de dados (entrada e saída da API)
- **Exception**: tratamento centralizado de erros

## 📦 Como rodar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Passos

```bash
# Clonar o projeto
git clone https://github.com/seu-usuario/task-manager.git
cd task-manager

# Compilar e rodar
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`

## 📖 Documentação da API

Após subir a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console

## 🔗 Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/tasks` | Listar todas as tarefas |
| GET | `/api/v1/tasks?status=TODO` | Filtrar por status |
| GET | `/api/v1/tasks?priority=HIGH` | Filtrar por prioridade |
| GET | `/api/v1/tasks/{id}` | Buscar por ID |
| GET | `/api/v1/tasks/search?keyword=texto` | Buscar por palavra-chave |
| GET | `/api/v1/tasks/summary` | Resumo estatístico |
| POST | `/api/v1/tasks` | Criar tarefa |
| PUT | `/api/v1/tasks/{id}` | Atualizar tarefa completa |
| PATCH | `/api/v1/tasks/{id}/status?status=DONE` | Atualizar status |
| DELETE | `/api/v1/tasks/{id}` | Remover tarefa |

## 📝 Exemplos de Requisição

### Criar tarefa
```http
POST /api/v1/tasks
Content-Type: application/json

{
  "title": "Estudar Spring Boot",
  "description": "Completar tutorial de API REST",
  "priority": "HIGH",
  "dueDate": "2024-12-31T23:59:00"
}
```

### Resposta
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Completar tutorial de API REST",
  "status": "TODO",
  "priority": "HIGH",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "dueDate": "2024-12-31T23:59:00"
}
```

## ✅ Testes

```bash
# Rodar todos os testes
mvn test
```

## 🎯 Boas Práticas Aplicadas

- ✅ **Arquitetura em camadas** (Controller → Service → Repository)
- ✅ **DTOs** para separar a API do banco de dados
- ✅ **Validação de entrada** com Bean Validation
- ✅ **Tratamento de erros** centralizado com `@RestControllerAdvice`
- ✅ **Exceções customizadas** (`TaskNotFoundException`)
- ✅ **Versionamento de API** (`/api/v1/`)
- ✅ **Códigos HTTP semânticos** (200, 201, 204, 404)
- ✅ **Documentação automática** com Swagger
- ✅ **Testes unitários** com JUnit 5 + Mockito
- ✅ **Logs** com SLF4J/Logback
- ✅ **Princípio DRY** (sem repetição de código)

## 🔮 Próximos Passos (Melhorias Futuras)

- [ ] Autenticação com Spring Security + JWT
- [ ] Migrar para PostgreSQL em produção
- [ ] Paginação na listagem de tarefas
- [ ] Testes de integração
- [ ] Deploy no Railway/Render

---
Desenvolvido como projeto de portfólio para demonstrar habilidades com desenvolvimento Backend em Java.
