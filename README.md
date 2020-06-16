# Rest-api 
[![Build Status](https://travis-ci.org/vladkondratuk/pm-rest-api.svg?branch=master)](https://travis-ci.org/github/vladkondratuk/pm-rest-api)

Java resp-api project to study technologies such as REST, Spring Framework(MVC), Hibernate ORM, Junit5, Mockito, MockMvc, CI/CD and others.

### Project Idea 
Rest-api for "Project management tool" demo project. A simple project to do in one month and gain experience for larger projects. 
I have idea to made my own "Project management tool" to learn new technologies and track time I spent on solving "tasks".

### Project Documentation
 
Steps to make this project:

- [Creating SQL commands for generating MySQL Schema](documentation/sql_schema.md) 
- [Set up Spring MVC using Java-based Configuration](documentation/mvc_config.md)
- [Set up Data Source](documentation/data_source.md)
- [Set up Hibernate using Java-based Configuration](documentation/hibernate_config.md)
- [Defining Entity with JPA and Hibernate](documentation/jpa_entity.md)
- [Creating DAO layer with Hibernate implementation](documentation/dao_layer.md)
- [Creating Service layer](documentation/service_layer.md)
- [Creating Spring MVC REST Controller layer](documentation/controller_layer.md)

Additions:
- [Spring IoC and DI](documentation/spring_ioc_di.md)
- [Spring MVC](documentation/spring_mvc.md)
- [Spring MVC Controllers](documentation/spring_controllers.md)

### Used technologies

 - jdk: 11-amazon-corretto java version
 - build tool: Maven
 - framework: Spring Framework
 - database: MySQL
 - ORM: Hibernate ORM
 - unit test: Junit5
 - mocks: Mockito, MockMvc
 - server: Tomcat embedded
 
### Prerequisites
 
         install git
         install JDK 11(amazon correto, openJDK)
         install maven3+
         install mysql workbench    
         
### Installing
Choose a directory for project, download project from github:
 
       $ git clone https://github.com/vladkondratuk/rest-api.git

#### Build project
Run terminal command in project directory:

        $ mvn clean install

#### Start app
Run terminal command in project directory:

        $ cd /rest-app
        $ mvn clean install cargo:run 

### How it's work
Simple example data conversion from MySQL table to POJO and then to JSON.

##### MySQL Table
```sql
    CREATE TABLE `activity` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `activity_name` varchar(128) DEFAULT NULL,
      PRIMARY KEY (`id`)
    )
```

##### Java class(JPA mapping)
```java
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    //Constructors
    //Getters and Setters    
}
``` 
##### JSON object(jackson plugin mapping)
```json
{
   "id": 1,
   "activityName": "activity example"
}
```
  
>Produced by Vladiskav Kondratuk 2020
