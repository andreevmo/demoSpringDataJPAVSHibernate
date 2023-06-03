package com.example.demoSpringVSHibernate;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "DemoSpringVsHibernateApplication API"))
@SpringBootApplication
public class DemoSpringVsHibernateApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringVsHibernateApplication.class, args);
    }

}
