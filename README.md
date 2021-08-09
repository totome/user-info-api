# User Info Api

### Overview

User-info-api is a simple application which interacts with the GitHub API in order to provide
data for a given user login.  

The App runs on port 8080 and exposes the following endpoint:
```
GET /users/{login}
```

### Example of usage

For the following request to localhost :
```
GET http://localhost:8080/users/octocat
```
the app provides the following response:
```
{
    "id": "583231",
    "login": "octocat",
    "name": "The Octocat",
    "type": "User",
    "avatarUrl": "https://avatars.githubusercontent.com/u/583231?v=4",
    "cratedAt": "2011-01-25T18:44:36Z",
    "calculations": "0.01537672988211173757047667862634546"
}
```
App collects information about the number of occurrences of requests in the embedded h2 db.

### Requirements

* Java JDK version 11 or later

### How to run

App is developed using maven (Tested on v3.8.1). For convenience, project folder contains a batch script
which is able to download and run maven automatically.

* to build execute: ./mvnw clean install
* to run execute: ./mvnw spring-boot:run

The App uses embedded H2 database (creates the db file in the project target dir). Once app is running it exposes standard web UI 
for db interactions. DB contains one table REQUESTS with logins (column LOGIN) and corresponding counts (REQUEST_COUNT). In order to open:

* run the app 
* go to http://localhost:8080/h2-console
* use the following credentials to log in:
  * JDBC url: jdbc:h2:./target/user-info-api-db
  * Username: sa
  * Password: password12345
