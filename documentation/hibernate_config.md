# Set up Hibernate using Java-based Configuration

Hibernate ORM is an object-relational mapping tool for the Java programming language. 
It provides a framework for mapping an object-oriented domain model to a relational database.

### Set up Hibernate properties

##### hibernate.dialect

The classname of a Hibernate org.hibernate.dialect.Dialect which allows Hibernate 
to generate SQL optimized for a particular relational database.

In most cases Hibernate will actually be able to choose the correct org.hibernate.dialect.Dialect 
implementation based on the JDBC metadata returned by the JDBC driver.

 e.g. full.classname.of.Dialect  

```hibernate.dialect=org.hibernate.dialect.MySQLDialect```

##### hibernate.show_sql

Write all SQL statements to console. This is an alternative to setting the log category 
org.hibernate.SQL to debug. 

 e.g. true | false 
 
 ```hibernate.show_sql=true```

###### Hibernate properties
```properties
#
# Hibernate properties
#
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.show_sql=true
```

###### getHibernateProperties() method
```java
 private Properties getHibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return props;
    }
```

### Set up Session Factory

SessionFactory is an interface. SessionFactory can be created by providing Configuration object, which will 
contain all DB related property details pulled from either hibernate.cfg.xml file or hibernate.properties file. 
SessionFactory is a factory for Session objects.

We can create one SessionFactory implementation per database in any application. If your application 
is referring to multiple databases, then you need to create one SessionFactory per database.

The SessionFactory is a heavyweight object; it is usually created during application start up 
and kept for later use. The SessionFactory is a thread safe object and used by all the threads of an application.

Here is the sample implementation for SessionFactory:

###### Bean sessionFactory() method
```java
    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }
``` 

Spring @Bean annotation tells that a method produces a bean to be managed by the Spring container. 
It is a method-level annotation. During Java configuration ( @Configuration ), the method is executed 
and its return value is registered as a bean within a BeanFactory.

A BeanFactory is like a factory class that contains a collection of beans. The BeanFactory holds Bean 
Definitions of multiple beans within itself and then instantiates the bean whenever asked for by clients. 
The BeanFactory is the actual container which instantiates, configures, and manages a number of beans.

 - ```sessionFactory.setDataSource(dataSource());``` - datasource property is used to provide the DataSource name 
 that will be used by Hibernate for database operations.
 
 - ```sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));``` - specifies Java 
 package to automatically scan for annotated entity classes.
 
 - ```sessionFactory.setHibernateProperties(getHibernateProperties());``` - provide database related 
 mappings from configuration file.
 
### Set up Hibernate Transaction Manager 

A transaction manager is a part of an application that controls the coordination of transactions over 
one or more resources. The transaction manager is responsible for creating transaction objects and 
managing their durability and atomicity.

###### Bean transactionManager(SessionFactory sessionFactory) method

In Spring, you can use @Autowired annotation to auto-wire bean on the setter method, constructor, or a field.

Injecting SessionFactory into HibernateTransactionManager.

```java
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
```

###### Data Source configuration class with Hibernate configuration
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
        // data source implementation...
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return props;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}
```

[Back to README](../README.md) 