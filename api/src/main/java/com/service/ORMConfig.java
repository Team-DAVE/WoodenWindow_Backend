// File: ORMConfig.java
// Configuration class for data source and hibernate
package com.service;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class ORMConfig {
    @Value("${connection.driver_class}")
    private String driverClassName;

    @Value("${connection.url}")
    private String dbUrl;

    @Value("${connection.username}")
    private String username;

    @Value("${connection.password}")
    private String password;

    @Value("${hibernate.db.dialect}")
    private String dbDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddl;

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(DataSource ds) {
        LocalSessionFactoryBean sfBean = new LocalSessionFactoryBean();
        sfBean.setDataSource(ds);
        sfBean.setPackagesToScan("com/model");
        sfBean.setHibernateProperties(getSfProps());
        return sfBean;
    }

    @Bean
    HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(sf);
        return manager;
    }

    private Properties getSfProps() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", dbDialect);
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddl);

        return props;
    }
}