# crudDemo
A small spring boot program that exposes REST endpoints for CRUD operations on a simple DB table.

Your need Apache Maven to build and need java 1.8+ to run the application.

## How to build

```shell
$ mvn clean package
```

## How to test

```shell
$ mvn clean test
```

## How to run application

```shell
mvn spring-boot:run
```
You can then access to the crud endpoints at http://localhost:8080/employees.
You can also import crudDemo.postman_collection.json into your postman app and test the endpoints.


## Design/Implementation notes:

1. An in-memory H2 DB is utilized to store employees table. The application can be easily configured to work with any DB. 

2. I created an interface for the service methods, so that a different implementation (e.g. getting data from kafka stream, or querying a 3rd party service, etc.) can be easily configured to provide the functionality.

3. Hibernate implementation of javax validations are utilized to validate the json input data.

4. ControllerAdvice is utilized to handle error response to the clients.

5. A dto object is utilized to provide a clean separation of entity DB object (employee) in favor clean abstraction of persistence layer. ModelMapper library is utilized to provide data transformation of dto and entity objects.

6. Lombok library is utilized for complie-time code auto generation (getters/setters/contrctors).

7. Comprehensive test cases are developed to provide unit testing and integration testing to ensure code quality and facilitate future enhancement/maintenance. 

8. Spring properties/configuration/injection are utilized to ease future enhancement/maintenance efforts. 

9. spring-boot-starter-actuator is included to provide production level monitoring/management tool.


### Requirements Description:

RESTful CRUD service
Summary

Build a simple RESTful CRUD service for managing an employee data.  The schema for the employee object is as follows, assume all fields are required. 
ID:  <Int32> 
Name:  <String 100 Characters>,
Office:  <String 4 Characters>, Range 100A – 599F,
Email: <String 150 Characters>,
Phone: <String 12 Characters>,
Role:  <String 150 Characters> 

Some Sample records are provided in the attached JSON file:

{
  "id" : 5,
  "name": "Michael Stone",
  "office": "321b",
  "email" : "michael.stone@oscorp.com",
  "phone" : "415.331.3321",
  "role" : "Teir 3 Support Engineer"
}

Service Requirements

•	The service should be written in Java
•	The service should provide basic CRUD functionality
o	Create 
o	Read (List, and single)
o	Update
o	Delete 
•	Records can be stored in memory persistence is not required. 
•	Treat this a live service. Include all of the elements you would expect to see in a production level system. 
•	Include a README file describing how to build and run the service 
 

