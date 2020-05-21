# Rest-api 
[![Build Status](https://travis-ci.org/vladkondratuk/rest-api.svg?branch=master)](https://travis-ci.org/github/vladkondratuk/rest-api)

Java project to study technologies such as REST, Spring Framework(MVC), Hibernate ORM, Junit5, Mockito, MockMvc, CI/CD and others.

###Project Idea 
A simple project to do in one month(I think) and gain experience for large projects. Rest-api for "Project management tool" demo project. 
I have idea to made my own "Project management tool" to learn new technologies and track time I spent on solve tasks.
P.S.For now is just rest-app on 3 entities. 

### Used technologies

 - jdk: 11-amazon-correto java version
 - build tool: Maven
 - framework: Spring Framework
 - database: MySQL
 - ORM: Hibernate ORM
 - unit test: Junit5
 - mocks: Mockito, MockMvc
 - server: Tomcat emdeded
 
### How it would look like
Simple example data conversion from MySQL table to Java object and JSON.

MySQL Table
```roomsql
    CREATE TABLE `activity` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `activity_name` varchar(128) DEFAULT NULL,
      PRIMARY KEY (`id`)
    )
```

Java class(JPA mapping)
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
JSON object(jakson plugin mapping)
```json
{
   "id": 1,
   "activityName": "activity example"
}
```    