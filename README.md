# Memoir

Memoir is a simple notes management application built with Java and SQLite.

## Project Report Snapshot

This project was delivered through a 6-month mentorship format (Jan-Jun 2026) focused on backend and DevOps readiness in a real team workflow.

- MVP scope completed end-to-end (CLI app + SQLite persistence)
- CI/CD pipeline and Docker containerization implemented
- 43 commits delivered through feature branches and pull request review
- Strong team outcome: 4.5/5 overall experience, 5/5 program recommendation

The full refined report is split into concise wiki-ready pages in `reports/`.

The project is designed as a learning exercise covering:

- Java fundamentals
- Layered architecture
- SQLite database integration
- Gradle build system
- Basic DevOps practices

## MVP Features

- Create a note
- List all notes
- View note by ID
- Delete a note
- SQLite database stored locally

## Project Structure

```text
.
├── build.gradle
├── settings.gradle
├── db/
│   └── schema.sql
├── src/
│   ├── main/
│   │   ├── java/memoir/
│   │   │   ├── model/
│   │   │   ├── db/
│   │   │   ├── service/
│   │   │   └── cli/
│   │   └── resources/
│   │       └── application.properties
│   └── test/java/memoir/
├── frontend/
└── README.md
```

## Prerequisites

- Java 21.0
- Gradle
- SQLite (via sqlite-jdbc)

## Usage

```sh
# Compile java project (UNIX)
./gradlew build
# (Windows)
gradle.bat build

# Run the application
./gradlew run
```
