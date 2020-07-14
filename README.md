# Project Overview 
 
### Requirement
You need to install :
- JDK 8
- Maven 3.6
- Mysql Server (TODO) - root/root

Architecture
============================

![architecture](architecture.png)

Component
============================

    .
    ├── ...
    ├── gateway                 # API gateway - Netflix Zuul
    ├── service-config          # Cloud configuration
    ├── service-customer        # Customer service - handling customer process: regiter user, retrieve user information
    ├── service-discovery       # Service registration - Netflix Eureka
    ├── service-prepaid-data    # Thirdparty Client
    ├── telecom                 # Thirdparty  
    ├── build                   #   
    └── ...
       
## Run
In /build folder run in order:

     java -jar service-discovery-0.0.1-SNAPSHOT.jar
     java -jar service-customer-0.0.1-SNAPSHOT.jar
     java -jar service-prepaid-data-0.0.1-SNAPSHOT.jar
     java -jar telecom-0.0.1-SNAPSHOT.jar
     java -jar gateway-0.0.1-SNAPSHOT.jar

Import NAB.postman_collection.json into postman and start testing.
    
### What's next
- Docker
- Log tracing
- Circuit breaker
