package com.demo.rest.api.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.demo.rest.api")
@PropertySource("classpath:persistence-mysql.properties")
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

    @Autowired
    private Environment env;

    public DataSource dataSource() {

        ComboPooledDataSource crmDataSource = new ComboPooledDataSource();

        try {
            crmDataSource.setDriverClass(env.getProperty("jdbc.driver"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        log.info("jdbc.url=" + env.getProperty("jdbc.url"));
        log.info("jdbc.user=" + env.getProperty("jdbc.user"));

        crmDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        crmDataSource.setUser(env.getProperty("jdbc.user"));
        crmDataSource.setPassword(env.getProperty("jdbc.password"));

        crmDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        crmDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        crmDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        crmDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));

        return crmDataSource;
    }

    private int getIntProperty(String propName) {
        return Integer.parseInt(Objects.requireNonNull(env.getProperty(propName)));
    }

    private Properties getHibernateProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return props;
    }

    public LocalSessionFactoryBean sessionFactory() {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}
