```markdown
# Insurance System Backend

**Description:**  
This project is a backend-only insurance management system built using Spring Boot. It provides RESTful APIs for managing customers, policies, claims, and agents. The system is designed to handle various types of insurance products and streamline operations such as policy creation, premium calculation, claim processing, and customer management.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Technologies Used](#technologies-used)
- [Configuration](#configuration)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Prerequisites
Before you begin, ensure you have the following installed:
- [Java 8+](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- [Maven 2.3+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)
- A preferred IDE (e.g., IntelliJ IDEA, Eclipse)

## Getting Started
1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/insurance-system-backend.git
    cd insurance-system-backend
    ```

2. Build the project using Maven:
    ```bash
    mvn clean install
    ```

3. Update the `application.properties` with your local configurations.

## Running the Application
To run the application, use the following command:

```bash
mvn spring-boot:run
```

Alternatively, you can run the JAR file:

```bash
java -jar target/insurance-system-backend.jar
```

## Testing
To run the tests, use the following command:

```bash
mvn test
```

## Technologies Used
- **Spring Boot** - Backend framework
- **Spring Data JPA** - ORM for database operations
- **Spring Security** - Authentication and authorization
- **Hibernate** - ORM for database interaction
- **MySQL** - Relational database management system
- **Lombok** - Java library for boilerplate code reduction
- **Swagger** - API documentation

## Configuration
Configuration files are located in the `src/main/resources` directory. Update the following properties in `application.properties` to match your environment:
- **Database Configuration:**
  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/insurance_db
  spring.datasource.username=root
  spring.datasource.password=yourpassword
  spring.jpa.hibernate.ddl-auto=update
  ```

- **Swagger Configuration:**
  Swagger UI is available at `/swagger-ui.html`.

- **Security Configuration:**
  ```properties
  spring.security.user.name=admin
  spring.security.user.password=adminpass
  ```

## API Endpoints
Here are some of the primary API endpoints:

### Customer Management
| Method | Endpoint              | Description                    |
| ------ | --------------------- | ------------------------------ |
| GET    | `/api/v1/customers`   | Retrieve all customers         |
| GET    | `/api/v1/customers/{id}` | Retrieve a customer by ID  |

### Policy Management
| Method | Endpoint              | Description                    |
| ------ | --------------------- | ------------------------------ |
| GET    | `/api/v1/policies`    | Retrieve all policies          |
| GET    | `/api/v1/policies/{id}` | Retrieve a policy by ID     |

### Claims Management
| Method | Endpoint              | Description                    |
| ------ | --------------------- | ------------------------------ |
| GET    | `/api/v1/claims`      | Retrieve all claims            |
| GET    | `/api/v1/claims/{id}` | Retrieve a claim by ID         |

## Database Schema
The database schema consists of the following key tables:
- **Customer:** Stores customer details like name, contact information, and address.
- **Policy:** Stores policy information such as type, coverage amount, and premium.
- **Claim:** Stores claims filed against policies, including status and payout.

## Contributing
Contributions are welcome! Please follow these steps to contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/YourFeature`).
5. Create a pull request.

## **DOCKER SECTION**

## **Step 1: Build the Docker Image**

Build the Docker image from the Dockerfile in the current directory. Use the `-t` flag to tag the image with a name (`insurance_crud` in this case).

```bash
docker build -t insurance_crud .
```

---

## **Step 2: Verify the Docker Image**

List all Docker images to verify that your image has been built successfully.

```bash
docker images
```

---

## **Step 3: Run the `insurance_crud` Container**

Run a Docker container from the `insurance_crud` image and map port `8081` on the host to port `8081` in the container.

```bash
docker run -p 8081:8081 insurance_crud
```

---

## **Step 4: Run a MySQL Container**

Run a MySQL container, specifying the version of MySQL (replace `version` with the actual version number).

```bash
docker run mysql:<version>
```

---

## **Step 5: Run MySQL Container with Environment Variables**

Run a MySQL container with specific environment variables:
- Expose and map port `3307` on the host to port `3306` in the container.
- Set the MySQL root password and database name.
- Run the container in detached mode (`-d`).

```bash
docker run -p 3307:3306 --name your_container_name \
  -e MYSQL_ROOT_PASSWORD=your_password \
  -e MYSQL_DATABASE=your_database_name -d mysql
```

---

## **Step 6: Create a Docker Network**

Create a Docker network named `networkmysql`.

```bash
docker network create networkmysql
```

---

## **Step 7: Connect MySQL Container to the Network**

Connect the previously created MySQL container to the `networkmysql` network.

```bash
docker network connect networkmysql your_container_name
```

---

## **Step 8: Rebuild the Docker Image (Optional)**

Build the Docker image again if changes have been made. Use the `-t` flag to tag the image with a name.

```bash
docker build -t your_image_name .
```

---

## **Step 9: Run the `insurance_crud` Container with MySQL Connection**

Run the `insurance_crud` container with environment variables for MySQL connection:
- Map port `8090` on the host to port `8081` in the container.
- Connect the container to the `networkmysql` network.
- Specify MySQL host, port, database name, user, and password.

```bash
docker run -p 8090:8081 --name insurancecrudcontainer --net networkmysql \
  -e MYSQL_HOST=your_mysql_container_name \
  -e MYSQL_PORT=3306 \
  -e MYSQL_DB_NAME=your_database_name \
  -e MYSQL_USER=your_mysql_user \
  -e MYSQL_PASSWORD=your_password your_image_name
```

---

## **Step 10: Verify Running Containers**

List all running containers to verify that your containers are running as expected.

```bash
docker ps
```

---

## **Application Properties Configuration**

Add the following configurations to your `application.properties` file:

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB_NAME:db_name}
spring.datasource.username=${MYSQL_USER:db_user}
spring.datasource.password=${MYSQL_PASSWORD:db_password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```
