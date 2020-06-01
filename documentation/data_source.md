# Set up Data Source

Before set up, let discus how establish connection to a data source and why to choose specific api.

### Establishing a Connection

First, you need to establish a connection with the data source you want to use. A data source can be a DBMS, 
a legacy file system, or some other source of data with a corresponding JDBC driver. Typically, 
a JDBC application connects to a target data source using one of two classes:

  - ```DriverManager:``` This fully implemented class connects an application to a data source, 
  which is specified by a database URL. When this class first attempts to establish a connection, 
  it automatically loads any JDBC 4.0 drivers found within the class path. Note that your 
  application must manually load any JDBC drivers prior to version 4.0.

  - ```DataSource:``` This interface is preferred over DriverManager because it allows details about 
  the underlying data source to be transparent to your application. A DataSource object's 
  properties are set so that it represents a particular data source.
  
##### Java JDBC 

  ```JDBC``` - Java Database Connectivity is an application programming interface for the programming 
 language Java, which defines how a client may access a database. It is a Java-based data access 
 technology used for Java database connectivity. It is part of the Java Standard Edition platform, 
 from Oracle Corporation.

##### Java JDBC Driver
  
  ```JDBC Driver``` is a software component that enables java application to interact with the database. 

### Why do we use a DataSource instead of a DriverManager?

 - ```DriverManager``` - is a static class in the Java™ 2 Plaform, Standard Edition (J2SE) and 
Java SE Development Kit (JDK). DriverManager manages the set of Java Database Connectivity 
(JDBC) drivers that are available for an application to use.

For ```DriverManager``` you need to know all the details (host, port, username, password, driver class) 
to connect to DB and to get connections. Externalizing those in a properties file doesn't change 
anything about the fact that you need to know them.

 - ```DataSource``` — is a interface was introduced in the JDBC 2.0 Optional Package API. 
It is preferred over DriverManager because it allows details about the underlying 
data source to be transparent to the application.

Using a ```DataSource``` you only need to know the JNDI name. The AppServer cares about the details 
and is not configured by the client application's vendor, but by an admin where the application is hosted.

##### Java JNDI

```JNDI``` - is the Java Naming and Directory Interface. It's used to separate the concerns of the application 
developer and the application deployer. When you're writing an application which relies on a database, 
you shouldn't need to worry about the user name or password for connecting to that database. 
JNDI allows the developer to give a name to a database, and rely on the deployer to map that name 
to an actual instance of the database.

### Set up Data Source

##### Set up data source configuration annotations.

```java
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.demo.rest.api")
@PropertySource("classpath:persistence-mysql.properties")
public class DataSourceConfig {
 
    /// ...
}
```

 - ```@Configuration``` - indicates that the class can be used by the 
 ```Spring IoC container``` as a source of bean definitions.
 
 The ```Spring IoC container``` is at the core of the Spring Framework. The container will create the objects, 
 wire them together, configure them, and manage their complete life cycle from creation till destruction. 
 The Spring container uses ```dependency injection (DI)``` to manage the components that make up an application.
 
 - ```@ComponentScan``` - using component scan is one method of asking Spring to detect Spring-managed components. 
 Spring needs the information to locate and register all the Spring components with the application 
 context when the application starts. Spring can auto scan, detect, and instantiate components 
 from pre-defined project packages. 

 - ```@PropertySource``` - is a convenient annotation for including PropertySource to Spring's 
Environment and allowing to inject properties into class attributes. 
(PropertySource is an object representing a set of property pairs from a particular source.) 

 - ```@EnableTransactionManagement``` - responsible for registering the necessary Spring components 
 that power annotation-driven transaction management, such as the TransactionInterceptor 
 and the proxy- or AspectJ-based advice that weave the interceptor into the call stack 
 when @Transactional methods are invoked.

##### Set up data source

Including properties for JDBC connection and connections pool with ```@PropertySource``` 
annotation to Spring's Environment and injecting properties into 
Data Source instance attributes with Environment interface.

###### Properties for JDBC connection and connections pool
```properties
#
# JDBC connection properties
#
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/project_management?useSSL=false
jdbc.user=springstudent
jdbc.password=springstudent

#
# Connection pool properties
#
connection.pool.initialPoolSize=5
connection.pool.minPoolSize=5
connection.pool.maxPoolSize=20
connection.pool.maxIdleTime=3000
``` 

###### DataSourceConfig class
```java
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.demo.rest.api")
@PropertySource("classpath:persistence-mysql.properties")
public class DataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

    private final Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        try {
            dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        log.info("jdbc.url={}", env.getProperty("jdbc.url"));
        log.info("jdbc.user={}", env.getProperty("jdbc.user"));

        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setUser(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        dataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        dataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        dataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        dataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return dataSource;
    }

    private int getIntProperty(String propName) {
        return Integer.parseInt(Objects.requireNonNull(env.getProperty(propName)));
    }
```

 - ```com.mchange.v2.c3p0.ComboPooledDataSource``` - it is implementation of Data Source from c3p0 library.

 - ```c3p0``` - is an easy-to-use library for augmenting traditional (DriverManager-based) 
   JDBC drivers with JNDI-bindable DataSources.

[Back to README](../README.md) 