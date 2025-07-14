# 📢 Announcement Management REST API

A simple REST API for managing announcements: creating, deleting, editing, and viewing announcements with view count increment.

---

## 🛠 Technologies

- Java 17  
- Spring Boot  
- Gradle  
- H2 (in-memory database)  
- JUnit + Mockito (for unit and integration tests)

---

## 🚀 Features

- ➕ Add an announcement (`POST /api`)
- ❌ Delete an announcement by ID (`DELETE /api/{id}`)
- ✏️ Edit announcement content (`PATCH /api/{id}`)
- 👁️ Get announcement by ID and increment view count (`GET /api/{id}`)
- 🛡️ Input validation and exception handling

---

## ▶️ How to Run

To run the application locally:

```bash
./gradlew bootRun

The API will be available at: http://localhost:8080/api

## ✅ Running Tests

To run all unit and integration tests:

```bash
./gradlew test

## 📂 Postman Collection
You can import the provided Postman collection (AnnouncementAPI.postman_collection.json) to test all endpoints with example requests.