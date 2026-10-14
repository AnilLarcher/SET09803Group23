# Docker Build and Run Test

## Purpose

Verify that the application image can be built and run in Docker for issue #24.

Test environment
- Tester: Shevanise Givans
- Test date: 12 October 2026
- Tested commit: 00f61e4
- Branch: feature/24-docker-build-run
- Host: macOS, x86_64
- Docker Engine: 29.6.1
- Docker Compose: 5.3.0
- Application base image: amazoncorretto:17
- Database: MySQL 8.0.32

## Setup

Reused the healthy database container in Compose project group23-issue21. A temporary override restricted the database's published host port to 127.0.0.1:3307. The repository's Compose file was unchanged.
The application image was built with:
docker compose -p group23-issue21 -f docker-compose.yml.yaml -f "$DB_OVERRIDE" build app
DB_OVERRIDE refers to the temporary local Compose override file used during testing. It is not a repository file.
Build result
Docker successfully built group23-issue21-app using amazoncorretto:17 and copied target/semApp.jar into the image. Result: PASS.
Container execution
Ran:
docker compose -p group23-issue21 -f docker-compose.yml.yaml -f "$DB_OVERRIDE" run --rm --no-deps --interactive app
The application used the Dockerfile's db:3306 connection address to reach the database over the Compose network.

## Observed results:
- The application printed Successfully connected.
- The interactive World Database menu appeared.
- Selecting option 2 returned country (239 rows) and country records.
- Selecting option 0 exited the application.
- The immediately checked exit status was 0.
- The database remained healthy afterward, with its host port restricted to 127.0.0.1:3307.
Result: PASS.
