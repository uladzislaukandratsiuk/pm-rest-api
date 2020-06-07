# Creating Service layer

### Intent

Service Layer is an abstraction over domain logic. Typically applications require multiple kinds of 
interfaces to the data they store and logic they implement: data loaders, user interfaces, integration 
gateways, and others. Despite their different purposes, these interfaces often need common interactions 
with the application to access and manipulate its data and invoke its business logic. The Service Layer 
fulfills this role.

  ##### Service Layer by Randy Stafford
  
  Defines an application's boundary with a layer of services that establishes a set of available 
  operations and coordinates the application's response in each operation.

### Service Design Pattern
  
 Service Layer when developing Spring MVC application, it is similar to the Service Layer Pattern that Martin 
 Fowler discusses in Patterns of Enterprise Application Architecture. It encapsulates your business logic and 
 make the controllers pretty thin. Basically the controllers use the service layer to get the domain models 
 that are then transformed into view models.
 
 A Service Layer defines an application's boundary and its set of available operations from the 
 perspective of interfacing client layers. It encapsulates the application's business logic, 
 controlling transactions and coordinating responses in the implementation of its operations.

##### Service API - TaskService interface example  

```java
public interface TaskService {

    List<Task> getTasks();

    Optional<Task> getTask(Long id);

    void saveTask(Task task);

    void deleteTask(Long id);
}
```

##### Service Implementation - TaskServiceImpl example  

```java
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    @Transactional
    public List<Task> getTasks() {
        List<Task> tasks = taskDao.findAll();
        if (tasks == null || tasks.isEmpty())
            throw new CustomEntityNotFoundException("No Tasks data found!");
        return tasks;
    }

    @Override
    @Transactional
    public Optional<Task> getTask(Long id) {
        Optional<Task> task = taskDao.findById(id);
        if (task.isEmpty())
            throw new CustomEntityNotFoundException("Task with id=" + id + " not found!");
        return task;
    }

    @Override
    @Transactional
    public void saveTask(Task task) {
        taskDao.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Optional<Task> task = taskDao.findById(id);
        if (task.isEmpty())
            throw new CustomEntityNotFoundException("Task with id=" + id + " not found!");
        taskDao.deleteById(id);
    }
}
```

### @Service - Spring annotation

We mark beans with ```@Service``` to indicate that it's holding the business logic. 
So there's no any other specialty except using it in the service layer.

### @Transactional - Spring annotation

One of the key points about ```@Transactional``` is that there are two separate concepts to consider, 
each with it's own scope and life cycle:

   - the persistence context
   - the database transaction

The transactional annotation itself defines the scope of a single database transaction. 
The database transaction happens inside the scope of apersistence context.

The persistence context is in JPA the EntityManager, implemented internally using 
an Hibernate Session (when using Hibernate as the persistence provider).

The persistence context is just a synchronizer object that tracks the state of a limited 
set of Java objects and makes sure that changes on those objects are eventually persisted back into the database.

#### Using Spring @Transactional

With Spring ```@Transactional,``` the above code gets reduced to simply this:

```java
@Transactional
public void businessLogic() {
... use entity manager inside a transaction ...
}
```

By using @Transactional, many important aspects such as transaction propagation
 are handled automatically. In this case if another transactional method is called 
 by businessLogic(), that method will have the option of joining the ongoing transaction.

One potential downside is that this powerful mechanism hides what is going on under 
the hood, making it hard to debug when things don't work.

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


### Dependency Injection

Dependency injection is a pattern through which to implement IoC, where the control being 
inverted is the setting of object's dependencies.

The act of connecting objects with other objects, or “injecting” objects into other objects, 
is done by an assembler rather than by the objects themselves.

##### Constructor-based Dependency Injection

```java
@Service
public class TaskServiceImpl implements TaskService {

   private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
    
    //...
}
```

[Back to README](../README.md) 