# Defining Entity with JPA and Hibernate

The Java Persistence API is a Java application programming interface specification that describes 
the management of relational data in applications using Java Platform, Standard Edition and Java Platform, 
Enterprise Edition/Jakarta EE. 

Entities in JPA are nothing but POJOs representing data that can be persisted to the database. 
An entity represents a table stored in a database. Every instance of an entity represents a row in the table.

### Defining entity

In order to define an entity, you must create a class that is annotated with the ```@Entity``` annotation.

By default, this entity will be mapped to the ```task``` table, as determined by the given class name. 
If you wanted to map this entity to another table (and, optionally, a specific schema) you could 
use the @Table annotation to do that. Here's how you would map the Task class to a ```"task"``` table:

```java
@Entity
@Table(name = "task")
public class Task {
    // implementation
}
```

### Mapping fields to columns

The ```@Column``` annotation allows us to define additional properties of the field/column, including length, 
whether it is nullable, whether it must be unique, its precision and scale (if it's a decimal value), 
whether it is insertable and updatable, and so forth.

```java
    @Column(name = "task_name")
    private String taskName;
```

### Specifying the primary key

One of the requirements for a relational database table is that it must contain a primary key, 
or a key that uniquely identifies a specific row in the database. In JPA, we use the ```@Id``` annotation 
to designate a field to be the table's primary key.

The ```@GeneratedValue``` annotation may be applied to a primary key property or field of an entity or mapped 
superclass in conjunction with the ```@Id``` annotation.

``` GenerationType.IDENTITY``` - indicates that the persistence provider must assign primary 
keys for the entity using a database identity column. 

In this example, we map the id attribute, which is an Long, to the ``id`` column in the ```task``` table:

```java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
```

### Relationships between entities

Generally the relations are more effective between tables in the database. Here the entity classes are 
treated as relational tables (concept of JPA), therefore the relationships between Entity classes are as follows:

    @ManyToOne Relation
    @OneToMany Relation
    @OneToOne Relation
    @ManyToMany Relation
    
##### @ManyToOne Relation
      
Many-To-One relation between entities: Where one entity (column or set of columns) 
is/are referenced with another entity (column or set of columns) which contain unique values. 
In relational databases these relations are applicable by using foreign key/primary key between tables.

##### @OneToMany Relation

In this relationship each row of one entity is referenced to many child records in other entity. 
The important thing is that child records cannot have multiple parents. In a one-to-many relationship 
between Table A and Table B, each row in Table A is linked to 0, 1 or many rows in Table B.

##### @OneToOne Relation
      
In One-To-One relationship, one item can belong to only one other item. It means each row of 
one entity is referred to one and only one row of another entity. 

##### @ManyToMany Relation
      
Many-To-Many relationship is where one or more rows from one entity are associated with 
more than one row in other entity. 
    
##### One-to-many and many-to-one relationships example

The @OneToMany and ```@ManyToOne``` annotations facilitate both sides of the same relationship. 
Consider an example where a ```Activity``` can have only one ```Task```, but an ```Task``` may have many ```activities```. 
The ```Activity``` entity would define a ```@ManyToOne``` relationship with ```Task``` and the ```Task``` entity would 
define a ```@OneToMany``` relationship with ```Activity```.

###### Task Class Entity
```java
@Entity
@Table(name = "task")
public class Task {
    
    //...

     @OneToMany(mappedBy = "task", fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH, CascadeType.MERGE,
                        CascadeType.PERSIST, CascadeType.REFRESH})
     private List<Activity> activities;

    //...
}
```

###### Activity Class Entity
```java
@Entity
@Table(name = "activity")
public class Activity {

    //...

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "task_id")
    private Task task;

    //...
}
```

In this case, the ```Task``` class maintains a list of all of the ```activities``` and 
the ```Activity``` class maintains a reference to its single ```Task```. Additionally, the ```@JoinColumn``` specifies 
the name of the column in the ```Activity``` table to store the ```ID``` of the ```Task```.

##### Fetch type

The default depends on the cardinality of the relationship. All to-one relationships use ```FetchType.EAGER ```
and all to-many relationships ```FetchType.LAZY```.

 - The ```FetchType.LAZY``` tells Hibernate to only fetch the related entities from the database when you 
use the relationship. This is a good idea in general because there’s no reason to select entities 
you don’t need for your uses case.

```java
@OneToMany(fetch = FetchType.LAZY)
```

 - The ```FetchType.EAGER``` tells Hibernate to get all elements of a relationship when selecting the root 
 entity. As I explained earlier, this is the default for to-one relationships.
 
```java
@ManyToOne(fetch = FetchType.EAGER)
```
 
##### JPA Cascade Type
      
All JPA-specific cascade operations are represented by the javax.persistence.CascadeType enum containing entries:
      
          ALL
          PERSIST
          MERGE
          REMOVE
          REFRESH
          DETACH

```java
@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
```

##### Hibernate Cascade Type
      
Hibernate supports three additional Cascade Types along with those specified by JPA. These Hibernate-specific 
Cascade Types are available in org.hibernate.annotations.CascadeType:
      
          REPLICATE
          SAVE_UPDATE
          LOCK

### Difference Between the Cascade Types

 - ```Cascade.ALL``` propagates all operations — including Hibernate-specific ones — from a parent to a child entity.
 
 - The persist operation makes a transient instance persistent. ```CascadeType.PERSIST``` propagates the persist 
 operation from a parent to a child entity. When we save the person entity, the address entity will also get saved.

 - The merge operation copies the state of the given object onto the persistent object with the same identifier. 
 ```CascadeType.MERGE``` propagates the merge operation from a parent to a child entity.

 - As the name suggests, the remove operation removes the row corresponding to the entity from the database 
 and also from the persistent context.
 ```CascadeType.REMOVE``` propagates the remove operation from parent to child entity. Similar to JPA's 
 ```CascadeType.REMOVE```, we have ``CascadeType.DELETE``, which is specific to Hibernate. There is no 
 difference between the two.
 
 - The detach operation removes the entity from the persistent context. When we use ```CascaseType.DETACH```, 
 the child entity will also get removed from the persistent context.
 
 - Unintuitively, ```CascadeType.LOCK``` re-attaches the entity and its associated child entity with the 
 persistent context again.
 
 - Refresh operations re-read the value of a given instance from the database. In some cases, we may change an 
 instance after persisting in the database, but later we need to undo those changes. In that kind of scenario, 
 this may be useful. When we use this operation with ```CascadeType.REFRESH```, the child entity also gets reloaded 
 from the database whenever the parent entity is refreshed.
 
 - The replicate operation is used when we have more than one data source, and we want the data in sync. 
 With ```CascadeType.REPLICATE```, a sync operation also propagates to child entities whenever performed on 
 the parent entity.
 
 - ```CascadeType.SAVE_UPDATE``` propagates the same operation to the associated child entity. It's useful when 
 we use Hibernate-specific operations like save, update, and saveOrUpdate. 

###### Task Class Entity example
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

[Back to README](../README.md)