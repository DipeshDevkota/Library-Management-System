# Library Management System

A REST API built with Java 17, Spring Boot, Spring Data JPA, and H2. It supports book and member management, searching, and book checkout/return. H2 stores data in `./data/librarydb` so it persists across restarts.

## Run

Install JDK 17+ and Maven 3.6.3+, then run:

```bash
mvn spring-boot:run
```

The API is available at `http://localhost:8080`. The H2 console is at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/librarydb`, user `sa`, blank password).

## Endpoints

| Method | Path | Description |
| --- | --- | --- |
| GET | `/api/books?search=tolkien` | List/search books |
| GET | `/api/books/{id}` | Get a book |
| POST | `/api/books` | Add a book |
| PUT | `/api/books/{id}` | Update a book |
| DELETE | `/api/books/{id}` | Delete a book (blocked if it has loan history) |
| GET | `/api/members` | List members |
| GET | `/api/members/{id}` | Get a member |
| POST | `/api/members` | Register a member |
| PUT | `/api/members/{id}` | Update a member |
| DELETE | `/api/members/{id}` | Delete a member (blocked if they have loan history) |
| GET | `/api/loans` | List all loans |
| GET | `/api/loans?active=true` | List active loans |
| GET | `/api/loans/member/{memberId}` | List a member's loan history |
| POST | `/api/loans` | Check out a book |
| POST | `/api/loans/{id}/return` | Return a checked-out book |

### Example requests

Add a book:

```json
{ "title": "The Hobbit", "author": "J.R.R. Tolkien", "isbn": "978-0547928227", "publicationYear": 1937, "totalCopies": 3 }
```

Register a member:

```json
{ "name": "Alex Reader", "email": "alex@example.com", "phone": "555-0100" }
```

Check out a book (loan period is 14 days):

```json
{ "bookId": 1, "memberId": 1 }
```

Errors return JSON with `timestamp`, `status`, `error`, and `message`. Duplicate ISBNs/emails, unavailable books, and invalid loan operations return conflict responses.
