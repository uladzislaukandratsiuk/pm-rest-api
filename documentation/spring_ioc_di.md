# Spring IoC and DI

### Inversion of Control

Inversion of Control is a principle in software engineering by which the control of objects or 
portions of a program is transferred to a container or framework. It's most often used in the 
context of object-oriented programming.

By contrast with traditional programming, in which our custom code makes calls to a library, 
IoC enables a framework to take control of the flow of a program and make calls to our custom 
code. To enable this, frameworks use abstractions with additional behavior built in. If we want 
to add our own behavior, we need to extend the classes of the framework or plugin our own classes.

#### The advantages of this architecture are:

   - decoupling the execution of a task from its implementation
   - making it easier to switch between different implementations
   - greater modularity of a program
   - greater ease in testing a program by isolating a component or mocking its 
   dependencies and allowing components to communicate through contracts

Inversion of Control can be achieved through various mechanisms such as: Strategy design pattern, 
Service Locator pattern, Factory pattern, and Dependency Injection (DI).

### Dependency Injection

Dependency injection is a pattern through which to implement IoC, where the control being 
inverted is the setting of object's dependencies.

The act of connecting objects with other objects, or “injecting” objects into other objects, 
is done by an assembler rather than by the objects themselves.

### Spring IoC Container

An IoC container is a common characteristic of frameworks that implement IoC.

In the Spring framework, the IoC container is represented by the interface ApplicationContext. 
The Spring container is responsible for instantiating, configuring and assembling objects 
known as beans, as well as managing their lifecycle.

The Spring framework provides several implementations of the ApplicationContext interface — 
ClassPathXmlApplicationContext and FileSystemXmlApplicationContext for standalone applications, 
and WebApplicationContext for web applications.

In order to assemble beans, the container uses configuration metadata, which can be in the 
form of XML configuration or annotations.

Dependency Injection in Spring can be done through constructors, setters or fields.

##### Constructor-based Dependency Injection example

```java
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.demo.rest.api")
@PropertySource("classpath:persistence-mysql.properties")
public class DataSourceConfig {

    private final Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        //...
    }
    
    //...
]
```

[Back to README](../README.md) 