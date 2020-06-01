# Set up Spring MVC using Java Configuration

### Spring Web MVC

Spring Web MVC is the original web framework built on the Servlet API.

To enable Spring MVC support through a Java configuration class, 
all we have to do is add the ```@EnableWebMvc``` annotation:

```java
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.demo.rest.api")
public class WebConfig {
 
    /// ...
}
```
 - ```@EnableWebMvc``` - enable Spring MVC-specific annotations like ```@Controller``` and ```@RestController```. 
 
 - ```@Configuration``` - indicates that the class can be used by the 
 ```Spring IoC container``` as a source of bean definitions.
 
 The ```Spring IoC container``` is at the core of the Spring Framework. The container will create the objects, 
 wire them together, configure them, and manage their complete life cycle from creation till destruction. 
 The Spring container uses ```dependency injection (DI)``` to manage the components that make up an application.
 
 - ```@ComponentScan``` - using component scan is one method of asking Spring to detect Spring-managed components. 
 Spring needs the information to locate and register all the Spring components with the application 
 context when the application starts. Spring can auto scan, detect, and instantiate components 
 from pre-defined project packages. 

### DispatcherServlet

Spring MVC, as many other web frameworks, is designed around the front controller pattern 
where a central ```Servlet```, the ```DispatcherServlet```, provides a shared algorithm for request processing, 
while actual work is performed by configurable delegate components. This model is flexible and 
supports diverse workflows.

The ```DispatcherServlet```, as any ```Servlet```, needs to be declared and mapped according to the ```Servlet``` 
specification by using Java configuration or in ```web.xml```. In turn, the ```DispatcherServlet``` uses Spring configuration
to discover the delegate components it needs for request mapping, view resolution, exception handling, and more.

The following example of the Java configuration registers and initializes the ```DispatcherServlet```, 
which is auto-detected by the Servlet container:

```java
public class WebAppInitializer implements WebApplicationInitializer {

    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.register(WebMvcConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        ServletRegistration.Dynamic servlet = servletContext.addServlet(
                DISPATCHER_SERVLET_NAME, dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}
```

 - ```AnnotationConfigWebApplicationContext``` is used to create application context for web applications by using java 
 clases as input for bean definitions instead of xml files. By default Spring use ```XmlWebApplicationContext``` 
 (an implementation of ```WebApplicationContext```) for creating spring container in web applications.

### Servlet Config

In a ```Servlet 3.0+``` environment, you have the option of configuring the Servlet container programmatically 
as an alternative or in combination with a ```web.xml``` file.

```WebApplicationInitializer``` is an interface provided by ```Spring MVC``` that ensures your implementation is detected and 
automatically used to initialize any ```Servlet 3.0+``` container.

[Back to README](../README.md) 


