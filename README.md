## Project to demo Spring Data JPA and Hibernate
Spring Boot(Web, Data JPA) and Hibernate training project

<a href="https://codeclimate.com/github/andreevmo/demoSpringDataJPAVSHibernate/maintainability"><img src="https://api.codeclimate.com/v1/badges/8179cd9f7e4e997f1dcb/maintainability" /></a>
<a href="https://codeclimate.com/github/andreevmo/demoSpringDataJPAVSHibernate/test_coverage"><img src="https://api.codeclimate.com/v1/badges/8179cd9f7e4e997f1dcb/test_coverage" /></a>
[![CI](https://github.com/andreevmo/demoSpringDataJPAVSHibernate/actions/workflows/build.yaml/badge.svg)](https://github.com/andreevmo/demoSpringDataJPAVSHibernate/actions/workflows/CI.yaml)

### Stack:
- Java 17;
- Gradle 7.5;
- Spring Boot (Web, Data JPA);
- Hibernate;
- H2, PostgreSQL;
- Lombok;
- JUnit;
- REST Assured;
- Plugins: checkstyle, jacoco.

### System requirements:
- Java 17;
- Gradle 7.+.

### Run:
- gradle bootRun - start application to http://localhost:5000;
- gradle testHibernate - test part of Hibernate implementation;
- gradle testSpringDataJPA - test part of Spring Data JPA implementation.

### Swagger:

http://localhost:5000/swagger-ui.html
