# Shop Application

## Description

### Genneraly

Application is a online shop which allows you to make purchases in different currencies. Applications offers managing users and products and making orders via PayU.

### Authorization

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


### Currency exchanging

Application everyday at 17:15 (may be configured from properties) downloads currency rates from NBP (National Bank of Poland). To revaluate money only the latest currency rate will be used. If something will go wrong with download, admin may download it manually via emergency curl request.

### Products

Authenticated employees may adding and update products.

### Payment

After selecting products with quantity user may make an order. All orders are revaluated to user reconcilation currency (setted in registration). After revaluation via NBP currency rates, reqest is send to PayU. If transaction will have success response redirect url will be returned to a user. User may perform transactions via all methods offered by PayU: blik, bank transfer etc. After successful transactions order will have status "PAID", and will be waiting for delivery. After delivery employee has to set "DELIVERED" status.

## Api Documentation
 URL: https://documenter.getpostman.com/view/13968610/UVysxFuy
