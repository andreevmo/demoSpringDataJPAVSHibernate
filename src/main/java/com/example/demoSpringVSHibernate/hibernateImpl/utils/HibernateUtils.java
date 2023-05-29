package com.example.demoSpringVSHibernate.hibernateImpl.utils;

import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;
import com.example.demoSpringVSHibernate.model.Role;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory;
    private static final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    private static SessionFactory createSessionFactory() {
        /*
        https://stackoverflow.com/questions/74045768/sqlfeaturenotsupportedexception-null-hikaridatasource
        Здесь ответ почему добавяляем properties вручную с кастомным именем файла
         */
        Properties properties = new Properties();
        try {
            if (System.getProperty("test") != null) {
                properties.load(classLoader.getResourceAsStream("hibernate-test.properties"));
            } else {
                properties.load(classLoader.getResourceAsStream("hibernateImpl.properties"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sessionFactory != null ? sessionFactory : new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Role.class)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Project.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return createSessionFactory();
    }
}
