# Java Code Styleguide

## Core Principles

### 1. Defensive Programming
- Always validate input parameters at the service and controller levels.
- Throw `IllegalArgumentException` or custom business exceptions for invalid inputs.
- Use `Optional` to handle potential null values from repositories.

### 2. Layered Architecture
- **Entity:** Represents database tables (JPA entities).
- **Repository:** Data access layer (Spring Data JPA).
- **Service:** Business logic layer. Always use interfaces and `impl` classes.
- **Controller:** REST API endpoints. Handle request mapping and parameter validation.
- **VO (View Object):** Data sent to the frontend.
- **DTO (Data Transfer Object):** Data received from the frontend or used between services.

### 3. Naming Conventions
- **Classes:** PascalCase (e.g., `PrescriptionService`).
- **Methods/Variables:** camelCase (e.g., `getPrescriptionById`).
- **Constants:** UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`).
- **Packages:** lowercase, dot-separated (e.g., `com.his.service.impl`).

### 4. Annotations & Dependencies
- Use **Lombok** to reduce boilerplate:
  - `@Slf4j` for logging.
  - `@RequiredArgsConstructor` for constructor-based dependency injection.
  - `@Data` or `@Getter/@Setter` for simple POJOs.
- Use **Spring Stereotypes**: `@Service`, `@RestController`, `@Repository`.
- Use `@Transactional` for service methods that modify data.

### 5. Logging
- Use `log.info()` for important business events.
- Use `log.error()` for exceptions and critical failures.
- Use `log.warn()` for unexpected but non-critical situations (e.g., resource not found).
- Include relevant IDs in log messages for traceability.

### 6. Comments
- Use Javadoc for public methods and classes.
- Explain *why* complex logic exists.
- Comments should be in Chinese (following the project's existing style) or English, but keep it consistent within a file.

### 7. API Design
- Return `Result<T>` for all controller endpoints (standardized response format).
- Use proper HTTP methods (GET, POST, PUT, DELETE).
- Follow RESTful path naming conventions.
