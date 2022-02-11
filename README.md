# Users Account Generator
## An API for Generating Random User data

### Used Stack
- Java8
- Maven
- Spring Boot
- Lombok
- H2 DB
- JavaFaker
- Java Persistence API
- JJWT for JWT authentication

### Endpoints
- `GET /api/users/generate?count={count}` generate `count` users and download it (No authorization required).
- `POST /api/users/batch` import users (No authorization required).
- `POST /api/auth` authenticate the user
- `GET /api/users/me` check the profile of authenticated user's profile 
- `GET /api/users/{username}` check the profile of `username`

### Run the project
After installing the dependencies, run `ApiApplication.java` to run the project.
