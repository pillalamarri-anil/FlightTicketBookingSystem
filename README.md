# FlightTicketBookingSystem — Help

## Overview
This Spring Boot application provides flight search, booking and user management. Code root is `src/main/java/dev/anil/flightticketbookingsystem`. Built with Java and Maven.

## Prerequisites
- JDK 17+ installed and `JAVA_HOME` set
- Maven (or use the included wrapper `mvnw` / `mvnw.cmd`)
- IntelliJ IDEA (project tested in IntelliJ IDEA 2024.3.7)

## Quick Start

Run using the Maven wrapper on Windows:
- Build: `.\mvnw.cmd clean package`
- Run: `.\mvnw.cmd spring-boot:run`
  Or run the packaged jar:
- `java -jar target\*.jar`

Run in IntelliJ:
- Import the project as a Maven project.
- Run the main class `dev.anil.flightticketbookingsystem.FlightTicketBookingSystemApplication`.

## Tests
- Execute tests: `.\mvnw.cmd test`

## Configuration
Application properties are in `src/main/resources/application.properties`. Add or update keys such as:
- `server.port`
- `spring.datasource.*`
- Provider/API credentials (example):
    - `amadeus.api.key`
    - `amadeus.api.secret`

If using environment variables or CI, pass them accordingly (Maven, system env, or container runtime).

## Project layout (high level)
- `src/main/java/dev/anil/flightticketbookingsystem/Configs` — configuration classes
- `src/main/java/dev/anil/flightticketbookingsystem/Controllers` — REST controllers
- `src/main/java/dev/anil/flightticketbookingsystem/InventoryProvider` — provider adapters:
    - `AmadeusAuth`
    - `AmadeusInventoryAdapter`
    - `SabreInventoryAdapter`
- `src/main/resources` — `application.properties`, static, templates
- Tests under `src/test/java/...`

## Common issues & fixes

- Problem: `AmadeusAuth` fields (API key/secret) are `null`.
    - Cause: Spring is not injecting configuration because the class or its consumer is not managed by Spring.
    - Fixes:
        - Ensure `AmadeusAuth` is a Spring bean (e.g. annotate with `@Component` or create it in a `@Configuration` class).
        - If `AmadeusInventoryAdapter` consumes `AmadeusAuth`, ensure the adapter is also a Spring-managed bean (annotate with `@Component`, `@Service`, or register it in a `@Configuration`).
        - Alternatively create a `@Configuration` method that builds `AmadeusInventoryAdapter` and injects `AmadeusAuth`.
        - Verify properties are present in `application.properties` (or environment) and bound properly (`@Value` or `@ConfigurationProperties`).
- Problem: Beans not auto-configured because class is not a component.
    - Fix: Add appropriate Spring stereotype or register beans explicitly in a `@Configuration`.

## Debugging tips
- Use breakpoints in IntelliJ on the application startup to ensure beans are created.
- Inspect Spring's bean list in logs or enable debug logging (`logging.level.org.springframework=DEBUG`) to see bean registration.

## Useful commands
- Build: `.\mvnw.cmd clean package`
- Run: `.\mvnw.cmd spring-boot:run`
- Test: `.\mvnw.cmd test`
- Run jar: `java -jar target\*.jar`

## Notes
- Keep secrets out of source control. Use environment variables or a secrets manager for API keys.
- For provider-specific wiring, prefer explicit configuration classes under `Configs` to centralize bean creation.
