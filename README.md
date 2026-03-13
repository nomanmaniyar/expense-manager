# Expense Manager API

A backend service built with pure **Spring Framework 6** (No Spring Boot) to manage and track daily expenses. It provides a robust REST API backed by an online PostgreSQL database hosted on Neon.

## 🚀 Tech Stack
* **Java 17**
* **Spring Framework 6** (MVC, Data JPA, ORM)
* **Hibernate 6**
* **PostgreSQL** (Hosted on Neon)
* **Embedded Tomcat 10** (Configured programmatically via `AppRunner.java`)
* **Maven**

---

## � Dependencies & Packages Used

Since this project is built using pure **Spring Framework 6** (without Spring Boot auto-configuration), each package is explicitly chosen and configured. Here is the breakdown of the primary dependencies used in `pom.xml`:

### Core Framework
* **`spring-context`**: Provides the core Spring Inversion of Control (IoC) container and dependency injection architecture.
* **`spring-webmvc`**: Enables REST API creation (`@RestController`, `@GetMapping`, etc.) and handles routing HTTP requests to the controller.
* **`spring-aop`** & **`aspectjweaver`**: Spring Aspect-Oriented Programming and its execution weaver. These are critical for intercepting method calls and enabling "magic" annotations like `@Transactional` and Spring proxy registrations.

### Database & ORM
* **`spring-orm`**: Integrates Spring with the underlying JPA implementation and provides declarative transaction management (`@Transactional`).
* **`spring-data-jpa`**: Provides the `JpaRepository` abstraction, greatly reducing boilerplate code for database CRUD operations.
* **`jakarta.persistence-api`**: The standard Jakarta Persistence (JPA) specification interface. It defines standard annotations like `@Entity`, `@Id`, `@Column`, etc.
* **`hibernate-core`**: The actual JPA provider (Object-Relational Mapper) that translates Java `Expense` entity objects into PostgreSQL queries and runs schema updates.
* **`postgresql`**: The PostgreSQL JDBC driver required for our application to communicate with the Neon cloud database.
* **`HikariCP`**: A lightweight and extremely fast database connection pool. It manages a pool of reusable database connections to prevent connection bottlenecks.

### Web Server
* **`tomcat-embed-core`**: The embedded Apache Tomcat server. Instead of building a WAR file and deploying it to an exterior server, we spin up Tomcat directly from our `main` method in `AppRunner.java`.
* **`jakarta.servlet-api`**: The standard Java Servlet API required by Tomcat and Spring's `DispatcherServlet` to intercept and manage HTTP requests.

### Data Binding & JSON
* **`jackson-databind`**: Automatically serializes Java objects to JSON (for API responses) and deserializes JSON into Java objects (for API POST bodies).
* **`jackson-datatype-jsr310`**: A Jackson extension module that natively handles modern Java 8 Date/Time objects (`LocalDate`, `LocalTime`), preventing dates from being serialized awkwardly as raw integer arrays.

---

## �🛠️ How to Run Locally

### 1. Database Configuration
Before running the application, make sure your PostgreSQL database credentials are set. We use `src/main/resources/application.properties` for this.

*(Note: Create this file if it doesn't exist, as it is ignored via `.gitignore` to protect your secrets).*

```properties
# src/main/resources/application.properties
jdbc.driverClassName=org.postgresql.Driver
# Note: Ensure you include ?sslmode=require if connecting to Neon or AWS RDS
jdbc.url=jdbc:postgresql://<your-db-host>:5432/<your_db_name>?sslmode=require
jdbc.user=your_username
jdbc.pass=your_password
```

### 2. Run the Server
Use Maven to compile and execute the embedded Tomcat application.

```bash
mvn clean compile exec:exec
```
*The server will start locally on port `8082`.*

---

## 🌐 API Endpoints

The API is mounted at `http://localhost:8082`.

### 1. Get all expenses
**GET** `/api/expenses`
Retrieves a JSON array of all tracked expenses.

**Example Response:**
```json
[
  {
    "id": "86ef2547-cb53-489a-878b-3afd63205688",
    "amount": 29.99,
    "currency": "USD",
    "category": "Food",
    "vendor": "Uber Eats",
    "paymentMode": "Apple Pay",
    "comment": "Late night dinner",
    "expenseDate": "2026-03-14",
    "expenseTime": "23:45:00"
  }
]
```

### 2. Get a single expense
**GET** `/api/expenses/{id}`
Retrieves a specific expense by its UUID.

**Example Response:**
```json
{
  "id": "86ef2547-cb53-489a-878b-3afd63205688",
  "amount": 29.99,
  "currency": "USD",
  "category": "Food",
  "vendor": "Uber Eats",
  "paymentMode": "Apple Pay",
  "comment": "Late night dinner",
  "expenseDate": "2026-03-14",
  "expenseTime": "23:45:00"
}
```

### 3. Create a new expense
**POST** `/api/expenses`
Creates a manual expense entry. 

**Request Body (JSON):**
```json
{
  "amount": 29.99,
  "currency": "USD",
  "category": "Food",
  "vendor": "Uber Eats",
  "paymentMode": "Apple Pay",
  "comment": "Late night dinner",
  "expenseDate": "2026-03-14",
  "expenseTime": "23:45:00"
}
```

**Example Response (201 Created):**
```json
{
  "id": "86ef2547-cb53-489a-878b-3afd63205688",
  "amount": 29.99,
  "currency": "USD",
  "category": "Food",
  "vendor": "Uber Eats",
  "paymentMode": "Apple Pay",
  "comment": "Late night dinner",
  "expenseDate": "2026-03-14",
  "expenseTime": "23:45:00"
}
```

### 4. Delete an expense
**DELETE** `/api/expenses/{id}`
Deletes the specific expense associated with the provided UUID. Returns `204 No Content` on success.

---

## 📂 Project Structure

This project uses modern programmatic Java Configuration for Spring rather than XML files:

* `AppRunner.java`: The main entry point. Bootstraps Apache Tomcat 10 programmatically.
* `config/WebAppInitializer.java`: Initializes the Spring DispatcherServlet.
* `config/WebMvcConfig.java`: Configures Spring MVC and JSON message converters (Jackson).
* `config/JpaConfig.java`: Configures the HikariCP connection pool, Hibernate, and the PostgreSQL data source.
* `model/Expense.java`: The JPA Entity representing the `expenses` database table.

---

## 💡 Upcoming Features
* **AI Message Parsing:** Integration with Spring AI to automatically decode incoming raw SMS text messages into structured `Expense` entities.
* **iOS Shortcut Webhook:** A dedicated endpoint to receive push notifications directly from an iPhone.
