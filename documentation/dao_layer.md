# Creating DAO layer with Hibernate implementation

The ```Data Access Object (DAO) pattern``` is a structural pattern that allows us to isolate the 
application/business layer from the persistence layer (usually a relational database, but it 
could be any other persistence mechanism) using an abstract API.

The functionality of this API is to hide from the application all the complexities involved in 
performing ```CRUD``` operations in the underlying storage mechanism. This permits both layers to 
evolve separately without knowing anything about each other.

### DAO Design Pattern

With DAO design pattern, we have following components on which our design depends:

   - The model which is transferred from one layer to the other.
   
   - The interfaces which provides a flexible design.
   
   - The interface implementation which is a concrete implementation of the persistence logic.

### Advantages of DAO pattern
  
  There are many advantages for using DAO pattern. Let’s state some of them here:
  
   1. While changing a persistence mechanism, service layer doesn’t even have to know where the data comes from. 
   For example, if you’re thinking of shifting from using MySQL to MongoDB, all changes 
   are needed to be done in the DAO layer only.
   
   2. DAO pattern emphasis on the low coupling between different components of an application. 
   So, the View layer have no dependency on DAO layer and only Service layer depends on it, even 
   that with the interfaces and not from concrete implementation.
   
   3. As the persistence logic is completely separate, it is much easier to write Unit tests for 
   individual components. For example, if you’re using JUnit and Mockito for testing frameworks, 
   it will be easy to mock the individual components of your application.
   
   4. As we work with interfaces in DAO pattern, it also emphasizes the style of “work with 
   interfaces instead of implementation” which is an excellent OOPs style of programming.

### Implementing DAO pattern
    
   With above mentioned components, let’s try to implement the DAO pattern. We will use 3 components here:
    
   1. The ```Task``` model which is transferred from one layer to the other.
   
   2. The ```TaskDao``` interface that provides a flexible design and API to implement.
   
   3. ```TaskDaoImpl``` concrete class that is an implementation of the ```TaskDao``` interface.



```Table``` class is the entity class which uses some basic ```Hibernate JPA annotations``` to 
be mapped to ```"task"``` table in the database.

Check [Defining Entity with JPA and Hibernate](jpa_entity.md) for JPA Entity documentation.

##### The Domain Class(model) - Hibernate entity
```java
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "priority")
    private String priority;

    @Column(name = "status")
    private String status;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "comment")
    private String comment;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Activity> activities;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id")
    private Project project;
    
    // Constructors
    // Getters and Setter
    // equals() and hashCode() 
    // toString()

}
```

The ```Task``` class is just a plain container for task data, so it doesn't implement any other behavior worth stressing.

Of course, the most relevant design choice that we need to make here is how to keep the application 
that uses this class isolated from any persistence mechanism that could be implemented at some point.

Well, that's exactly the issue that the ```DAO pattern``` attempts to address.


### The DAO API

Let's define a basic ```DAO layer```, so we can see how it can keep the domain model completely 
decoupled from the persistence layer.

```DaoRepository``` interface defines an abstract API that performs ```CRUD``` operations on objects of type ```T``` 
with an identifier of type ```ID```.

Due to the high level of abstraction that the interface provides, it's easy to create a concrete, 
fine-grained implementation that works with Task objects.

Here's the DAO API:

##### The DaoRepository interface

```java
public interface DaoRepository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    void save(T entity);

    void deleteById(ID id);
}
```

Since we have few domain classes it makes sense to more specific interface for each one: 

##### The TaskDao interface
```java
public interface TaskDao extends DaoRepository<Task, Long> {
}
```

The ```TaskDaoImpl``` class implements all the functionality required for fetching, updating, and removing ```Task``` objects.

Let's define a task-specific implementation of the ```TaskDao``` interface:

##### The TaskDaoImpl class
```java
@Repository
public class TaskDaoImpl implements TaskDao {

    private final SessionFactory sessionFactory;

    public TaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Task> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery("from Task", Task.class);
        return query.getResultList();
    }

    @Override
    public Optional<Task> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Task task = session.get(Task.class, id);
        return Optional.ofNullable(task);
    }

    @Override
    public void save(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(task);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Task where id=:taskId");
        query.setParameter("taskId", id);
        query.executeUpdate();
    }
}
```

The ```@Repository``` annotation for persistence layer is a marker for any class that fulfils the role or stereotype 
of a repository (also known as Data Access Object or DAO). Among the uses of this marker is the automatic 
translation of exceptions.

While both the ```Task``` and ```TaskDaoImpl``` classes coexist independently within the same application, we still need to 
see how the latter can be used for keeping the persistence layer hidden from application logic.
 
Check [Service layer](service_layer.md) to see how ```TaskDao``` interface can be used.

### Hibernate implementation

#### Dependency Injection

Check [Spring IoC and DI](spring_ioc_di.md) for more about Inversion of Control and Dependency Injection. 

```java
@Repository
public class TaskDaoImpl implements TaskDao {

    private final SessionFactory sessionFactory;

    public TaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    //...
}
```

#### Session Factory

Hibernate ```SessionFactory``` is the factory interface through which we get ```sessions``` and perform database operations. 

The main contract here is the creation of ```Session``` instances. Usually an application has a single 
```SessionFactory``` instance and threads servicing client requests obtain ```Session``` instances from this factory.

The internal state of a ```SessionFactory``` is immutable. Once it is created this internal state is set. 
This internal state includes all of the metadata about ```Object/Relational Mapping```. 

First of all, ```getSessionFactory()``` is a static method that provides a ```SessionFactory```, the creator 
of ```Sessions```, the basic interfaces between a Java application and Hibernate. 

```java
Session session = sessionFactory.getCurrentSession();
```

#### The Hibernate Query Language

Hibernate uses a powerful query language ```(HQL)``` that is similar in appearance to ```SQL```. 
Compared with ```SQL```, however, ```HQL``` is fully object-oriented and understands notions like inheritance, 
polymorphism and association.


- Query

    - Query refers to ```JPQL/HQL``` query with syntax similar to ```SQL``` generally used to execute DML 
    statements(CRUD operations).
    
    - In ```JPA```, you can create a query using ```entityManager.createQuery()```. 
    You can look into API for more detail.
    
    - In Hibernate, you use ```session.createQuery()```.

- NativeQuery

    - Native query refers to actual sql queries (referring to actual database objects). 
    These queries are the sql statements which can be directly executed in database using a database client.
    
    - JPA : ```entityManager.createNativeQuery()``` 
    
    - Hibernate (Non-JPA implementation): ```session.createSQLQuery()```

-  NamedQuery

    - Similar to how the constant is defined. NamedQuery is the way you define your 
    query by giving it a name. You could define this in mapping file in hibernate 
    or also using annotations at entity level.

- TypedQuery

     - TypedQuery gives you an option to mention the type of entity when you create a 
     query and therefore any operation thereafter does not need an explicit cast to the 
     intended type. Whereas the normal Query API does not return the exact type of 
     Object you expect and you need to cast.
 
#### Hibernate implementation example

##### List< Task > findAll() method implementation

```java
@Override
    public List<Task> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Task> query = session.createQuery("from Task", Task.class);
        return query.getResultList();
    }
```

```SessionFactory``` creates ```Session``` instance.

```java
Session session = sessionFactory.getCurrentSession();
```

The simplest possible Hibernate query is of the form: 

```java
Query<Task> query = session.createQuery("from Task", Task.class);
```

This returns all instances of the class eg.Task.

##### void save(Task task) method implementation

```java
    @Override
    public void save(Task task) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(task);
    }
```

```SessionFactory``` creates ```Session``` instance.

```java
Session session = sessionFactory.getCurrentSession();
```

You must use ```saveOrUpdate()``` method, to handle case where we had to save persistent entity in 
another session and that got resulted in duplicate key error. You should use ```saveOrUpdate() ```
with even non-persistent entities.

```java
session.saveOrUpdate(task);
```

```SaveOrUpdate()``` calls either ```save()``` or ```update() ```on the basis of identifier exists or not. e.g 
if identifier does not exist, ```save()``` will be called or else ```update()``` will be called.

[Back to README](../README.md)