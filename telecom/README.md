# NAB Third-party API

Simple project for demonstrating the communication between NAB system and another.

First client have to register in order to exchange client_id, client_secret and RSA key pair.

Client should bypass their client_id and client_secret (using HmacSHA256) for authentication and use Pub key for encryption the response token.  
## Requirement
You need to install :
- JDK 8
- Maven 3.6

## Build

    mvn clean install
    
## Run

    mvn spring-boot:run

Folder Structure Conventions
============================
### A Source directory layout

    .
    ├── ...
    ├── com.thinhnguyen.provider.telecom
    │   ├── config          # Additional configuration: security  
    │   ├── controller      # Rest controllers
    │   ├── dto             # DTO
    │   ├── exception       # Exception definination - handler
    │   ├── respository     # Database respository
    │   └── service         # Business Logic 
    └── ...
    
### Voucher Code Retrieval Process
To make it simple, this service allow client have life-time access (do not need a access_token)    