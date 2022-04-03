# Shop Application

## Description

### Genneraly

Application is a online shop which allows you to make purchases in different currencies.

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

Attempt | #1 | #2 | #3 | #4 | #5 | #6 | #7 | #8 | #9 | #10 | #11
--- | --- | --- | --- |--- |--- |--- |--- |--- |--- |--- |---
Seconds | 301 | 283 | 290 | 286 | 289 | 285 | 287 | 287 | 272 | 276 | 269


### Currency exchanging

### Payment

## Api Documentation
 URL: https://documenter.getpostman.com/view/13968610/UVysxFuy