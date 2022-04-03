# Shop Application

## Description

## Genneraly

Application is a online shop which allows you to make purchases in different currencies. Applications offers managing users and products and making orders via PayU.

## Authorization

After successful login user will get 2 JWT tokens:
 - Access token that gives permissions to resources for logged user
 - Refresh token which is used for refresh access token after it will be expired.

Each token is signed by the application by HMAC256 algorithm.

Tokens store informations about:
- Used algorithm (always HMAC256)
- Token type (always JWT)
- Expire date (may be configured from applicatio properties)
- Roles (access token only)

Endopoints authorization:

Enpoint | Authorization
--- | --- 
/public | No Authorization
/login | No Authorization
/authorization | No Authorization
/employee | Has role Employee
/admin | Has role Admin
Other | Authenticated user


## Currency exchanging

Application everyday at 17:15 (may be configured from properties) downloads currency rates from NBP (National Bank of Poland). To revaluate money only the latest currency rate will be used. If something will go wrong with download, admin may download it manually via emergency curl request.

## Products

Authenticated employees may adding and update products.

## Payment

After selecting products with quantity user may make an order. All orders are revaluated to user reconcilation currency (setted in registration). After revaluation via NBP currency rates, reqest is send to PayU. If transaction will have success response redirect url will be returned to a user. User may perform transactions via all methods offered by PayU: blik, bank transfer etc. After successful transactions order will have status "PAID", and will be waiting for delivery. After delivery employee has to set "DELIVERED" status.

## How to run?

Initial database schema will be created by flyway with initial user that will have all roles. Username: admin, Password: admin

Set necessary informations to application.yml file like:
- server.port (default 20000)
- config.postgres.url - url to database
- config.postgres.db-name - db name
- config.postgres.username - db username
- config.postgres.password - db upassword

Requirements:
- Java version 17
- Java and Maven added to enviroment variables

Run:
1. 'mvn -U clean install' from main project directory
2. 'java -jar target/shop-app-{project version}.jar' from main project directory

## Api Documentation
 URL: https://documenter.getpostman.com/view/13968610/UVysxFuy
 
## Technology stack (mainly):
- Java 17
- Spock framework
- Spring Boot
- Spring security
- Spring webflux
- Spring JPA
- QueryDSL
- Flyway
- Lombok
- Auth0 - JWT management
- Neovisioners library (Currency codes)
- Joda Money
- Jadira
