# nycreates-crud-jsp
sample application to be used for candidate screening for validating java skills

---

## prerequisites

* java 11
* maven

## maven commands

**install app**

`mvn clean install`

**run app**

`mvn spring-boot:run`

## app reference

**h2 (database)**

local: http://localhost:9999/h2-console

seed: [data.sql](src/main/resources/data.sql)

description: an in-memory database engine [More...](https://www.h2database.com/html/main.html)

**swagger**

local: http://localhost:9999/swagger-ui.html
  
description: 
a tool that allows visualization and interaction with APIs [More...](https://swagger.io/tools/swagger-ui/)


## app structure

* annotation - customer annotations
* config - application configuration
* controller - application controllers
* controller/advice - exception handling
* dao/model - database object models (Hibernate) [More...](https://hibernate.org/orm/)
* dao/repository - database repositories (Spring Data) [More...](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories)
* dao/specification - database specifications (Spring Data) [More...](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/)
* enumerator - custom enums
* exception - custom exceptions
* model/dto - data transfer objects 
* model/filter - retrieve filter objects (Specification)
* service - business logic layer
* utility - helper utility classes
* validator - validation logic (Spring Validation) [More...](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/validation.html)

