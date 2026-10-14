# Database Connection Test

## Purpose
Verify the World database connection and sample queries for issue #21, including access through the Java application.
Test environment
- Tester: Shevanise Givans
- Test date: 11 October 2026
- Tested commit: 00f61e4
- Branch: feature/21-database-connection-test
- Java: Microsoft OpenJDK 17.0.20.1
- Maven: 3.10.0
- Docker Engine: 29.6.1
- Docker Compose: 5.3.0
- MySQL: 8.0.32

## Database setup

SQL checks
1. SELECT 1;
   Expected: 1. Actual: 1. Result: PASS.
2. SHOW TABLES;
   Expected: city, country and countrylanguage. Actual: all three tables were listed. Result: PASS.
3. SELECT COUNT(*) AS country_count FROM country;
   Expected: 239. Actual: 239. Result: PASSSS.
4. SELECT c.Name AS Country, ci.Name AS Capital FROM country AS c JOIN city AS ci ON ci.ID = c.Capital WHERE c.Code = 'GBR';
   Expected: United Kingdom and London. Actual: United Kingdom and London. Result: PASS.

## Java application check
Ran mvn package. Maven reported BUILD SUCCESS.
Started the application with:
java -jar target/semApp.jar 127.0.0.1:3307 1000
The application printed Successfully connected and displayed its menu. Selecting option 2 returned country (239 rows) and country records.
Result: PASS. The Java application connected to MySQL through the published local port and retrieved data successfully.

## Scope and limitations
No application code or database schema changes were needed. Docker Compose also displayed an obsolete version-field warning, which did not prevent configuration validation or startup.