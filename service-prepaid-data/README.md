# NAB Third-party Client Service

A rest client api service. Retrieve voucher code from third party and handling networking issues. 
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
    ├── com.thinhnguyen.nab.service.prepaiddata
    │   ├── client          # Feign HTTP client. Used for calling internal service
    │   ├── config          # Additional configuration
    │   ├── controller      # Rest controllers
    │   ├── dto             # DTO
    │   ├── rest            # Another HTTP client (retrofit2 - my favorite). Used for calling thirdparty 
    │   ├── exception       # Exception definination - handler
    │   ├── tools           # Tools - 
    │   └── service         # Business Logic 
    └── ...
    
### Communication Flow
To deal with networking scenarios, I came up with an solution



Because of notification-service is not developed, so I change my approach - calling customer-service for updating delayed voucher, user can retrieve it latter

