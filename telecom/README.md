# NAB Third-party API

Simple project for demonstrating the communication between NAB system and another.

Client need register with this system.   
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