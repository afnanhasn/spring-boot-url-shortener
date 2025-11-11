# 🌐 spring-boot-url-shortener

A web application to shorten URLs using Spring Boot and PostgreSQL. Supports guest and login modes, with an admin dashboard for management.

---

## 🚀 Tech Stack

- **Backend**: Spring Boot, Java  
- **Frontend**: HTML, CSS, Thymeleaf  
- **Database**: PostgreSQL (visualized via DBeaver)  
- **Build Tool**: Maven  
- **Containerization**: Docker & Docker Compose  

---

## 📌 Requirements

### 🔗 Shorten URL
- Accept a long URL and return a shortened URL
- Ensure the shortened URL is unique
- Validate input URL for correctness (optional/configurable)
- Allow guest users to create public shortened URLs with a default 30-day expiration
- Allow authenticated users to:
  - Create public or private shortened URLs with custom expiration time
  - View and delete their own shortened URLs

### 🔁 Redirection
- Redirect to the original long URL when a shortened URL is accessed
- Handle invalid or expired shortened URLs gracefully

### 📊 Analytics
- Track the number of clicks for each shortened URL

### 👥 User Management
- Allow users to register and login
- Admin users can view all shortened URLs (including public and private)
---

## 🧰 Local Setup Instructions

### 📦 Prerequisites

- JDK installed (Java 17+ recommended)  
- IntelliJ IDEA (or any IDE)  
- Docker Desktop  

### 🛠️ Steps to Run Locally

1. **Clone the repository**  
   ```bash
   git clone https://github.com/yourusername/spring-boot-url-shortener.git
   ```

2. **Open in IntelliJ** and let Maven resolve dependencies

3. **Start Docker Desktop**

4. **Run Docker Compose**  
   ```bash
   docker-compose up
   ```  
   This will spin up a PostgreSQL container on `localhost:5432`

5. **Run the Spring Boot application**  
   - Use IntelliJ's run configuration  
   - Or via terminal:  
     ```bash
     mvn spring-boot:run
     ```

6. **Access the app**  
   - Visit `http://localhost:8080` in your browser  

---

## 📌 Notes

- PostgreSQL credentials and ports are configured in `docker-compose.yml`  
- DBeaver can be used to inspect and visualize database tables  
- REST API support is planned for future releases  

---

## 🙋 Author

**Mohd Afnan Hasan**  
Backend Engineer | [LinkedIn ](https://www.linkedin.com/in/afnan-hasan-2b29791a0/)

---

## 🚫 License

This project is currently unlicensed. All rights reserved by the author.
