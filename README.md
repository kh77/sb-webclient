# Spring Boot - WebClient Example

### **WebClient**
- Block & Non Blocking Calls 
- Using WireMock for Stub port(9090): when WebClient will call the service
  - Flow of Service
  
       Controller ---- WebClient ---- WireMock 

**Tools**
- Java 1.11 & Spring Boot 2.7
- Run schema.sql script in my sql 
- mvn clean compile



### Curl Endpoint:

- Get Invoice by ID:


    curl -X GET http://localhost:8080/bill/{id}


- Create Invoice:
  
      curl -X POST http://localhost:8080/bill -H "Content-Type: application/json" -d '{"amount":"100.00"}'


- Update Invoice by ID:

  
      curl -X PUT http://localhost:8080/bill/{id} -H "Content-Type: application/json" -d '{"amount":"150.00"}'


- Delete Invoice by ID:


      curl -X DELETE http://localhost:8080/bill/{id}


- Create Invoice asynchronously:



      curl -X POST http://localhost:8080/bill/async -H "Content-Type: application/json" -d '{"amount":"200.00"}'


- Get Invoice by ID asynchronously:

  

      curl -X GET http://localhost:8080/bill/async/{id}



- Update Invoice by ID asynchronously:


      curl -X PUT http://localhost:8080/bill/async/{id} -H "Content-Type: application/json" -d '{"amount":"250.00"}'


- Delete Invoice by ID asynchronously:


      curl -X DELETE http://localhost:8080/bill/async/{id}


