# URL Shortener 🔗

A full-stack URL Shortener web application built with **Java Spring Boot**, **Thymeleaf**, **MySQL**, and **Docker**.

## ✨ Features

- Shorten any long URL instantly
- Clean and responsive web UI built with Thymeleaf
- Redirects short URLs to original URLs
- Prevents duplicate entries in the database
- Base62 encoding algorithm for generating short URLs
- Containerized with Docker for easy deployment
- REST API + Web Interface support
- Health check via Spring Boot Actuator

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 2.7 |
| Frontend | Thymeleaf, HTML, CSS |
| Database | MySQL 8 |
| ORM | Spring Data JPA |
| Validation | Apache Commons Validator |
| Build Tool | Gradle |
| Containerization | Docker, Docker Compose |

## 🚀 Getting Started

### Prerequisites
- Docker Desktop installed and running
- Java 17+
- Gradle

### Run the Project

```bash
git clone https://github.com/Lekha15-cse/url-shortener-springboot.git
cd url-shortener-springboot
./gradlew build -x test
docker-compose up --build
```

App runs at: **http://localhost:9090**

## 📖 Usage

### Web UI
Open browser at `http://localhost:9090` and paste any URL to shorten it.

### REST API

**Shorten a URL:**
```http
POST /shorten
Content-Type: application/json

{ "fullUrl": "https://example.com/very/long/url" }
```

**Response:**
```json
{ "shortUrl": "http://localhost:9090/ab" }
```

**Redirect:**
```http
GET /{shortCode} -> redirects to original URL
```

**Health Check:**
```http
GET /actuator/health
```

## 🔢 URL Shortening Algorithm

Uses **Base62 encoding** on the auto-generated database ID to produce short, readable codes. Even the maximum Long value produces only 10 characters.

## 📁 Project Structure

```
src/main/java/com/lekha/urlshortener/
+-- controller/     # Handles HTTP requests (REST + Web)
+-- service/        # Business logic
+-- repository/     # Database access layer
+-- model/          # JPA Entity
+-- dto/            # Data Transfer Objects
+-- common/         # Utility classes (Base62, URL utils)
+-- error/          # Custom error handling
```

## 🐳 Docker Setup

Two containers:
- **api-server** — Spring Boot app
- **api-db** — MySQL 8 database

Connected via `urlshortener-mysql-network` Docker network.

## 🔮 Future Enhancements

- [ ] Click counter — track how many times each link is visited
- [ ] Link expiry — auto-expire URLs after N days
- [ ] Custom alias — let users pick their own short code
- [ ] QR Code generation for each short URL
- [ ] JWT Authentication — user login and link management
- [ ] Rate limiting — prevent abuse

## 👤 Author

**Lekha Pandi**  
GitHub: [@Lekha15-cse](https://github.com/Lekha15-cse)
