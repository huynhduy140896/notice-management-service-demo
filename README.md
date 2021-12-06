# notice-management
## Identity Authorization Service
A REST API implement notice registration, modification, deletion, and inquiry. 
Use JWT to get user token and use it for the next step

## Table of contents
- [Feature Requirements](#Feature-Requirements)
- [Problem solving strategies](#Problem-solving-strategies)
- [Technologies](#Technologies)
- [Installation](#installation)
- [Tests](#tests)


## Feature Requirements
**Implement notice registration, modification, deletion, and inquiry API.**
- **Registering Notice:** the input items are as follows
  - Title, content, notice start date and time, notice end date and time, attached files (multiple)
- **Inquiry notice:** Responses to notice inquiry are as follows
  - Title, content, registration date, number of views, author

**Non-functional requirements and evaluation items**
- Implemented as REST API.
- The development language is Java.
- Web framework uses Spring Boot.
- Persistence framework uses Hibernate
- No restrictions on database
- Writing unit/integration tests for functions and constraints
- Implementation considering large-capacity traffic

## Problem solving strategies
- **Project skeleton:** create a spring boot project using JPA for simple registration, modification, deletion, and inquiry notice 
- **Authentication with JWT:** Generate JWT Access token and a random string for Refresh token the login, use login info in session to check authorization and authority, set author and modifier for notice
- **Controller Advice:** customize class for handling exception.
## Technologies
- Spring boot
- Hibernate
- JWT
- Mockito
## Prerequisites
- JDK 11
- Maven
- H2 Database

## Installation
- Clone the repository
```bash
$ git clone https://github.com/huynhduy140896/notice-management-service-demo.git
```

- Install dependencies
```bash
$ cd [project_name]
$ mvn install
```
- Create the configuration file and update with your local config
```bash
$ cd src/main/resources
$ cp application-example.properties application.properties
```
- Start Application
```bash
$ mvn spring-boot:run
```
- Using postman 
  Please import file below to postman (https://github.com/huynhduy140896/notice-management-service-demo/blob/master/src/main/resources/Postman_collection/Notice-management.postman_collection.json)

How to use:
  I had imported an admin account as a initial data into database (password is encoded):
    ![image](https://user-images.githubusercontent.com/43404038/144809221-4f86ed37-9ee8-465c-a94f-62e4c063da80.png)

  So you can use it to get the token with api login (username: petter, password: 123456):
    ![image](https://user-images.githubusercontent.com/43404038/144809322-6ce45047-c77f-47b6-b8f6-e27f3e72930a.png)

  Or else, you can create a new account by using api register:
    ![image](https://user-images.githubusercontent.com/43404038/144809695-c428f164-9831-443a-b1a1-0e9066928b1d.png)
  and try to login:
    ![image](https://user-images.githubusercontent.com/43404038/144809819-464dab9b-6bca-42c8-9a71-0f318dc665d3.png)

  You can only access to another api onece got the user token, if not or incorrect, you will got the error Unauthorized
    ![image](https://user-images.githubusercontent.com/43404038/144810119-515224a3-4d03-4393-8fd0-cc76a12ce2db.png)

## Tests
Using mockito
