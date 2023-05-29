## Project to demo Spring Data JPA and Hibernate

### Stack:
- Java 17;
- Gradle 7.5;
- Spring Boot (Web, Data JPA);
- Hibernate;
- H2, PostgreSQL;
- Lombok;
- JUnit;
- REST Assured.

### System requirements:
- Java 17;
- Gradle 7.+.

### Run:
- gradle bootRun: start application to http://localhost:5000;
- gradle testHibernate: test part of Hibernate implementation;
- gradle testSpringDataJPA: test part of Spring Data JPA implementation.

### Routing:

- http://localhost:5000/app/spring  + /employees | /projects | /roles
- http://localhost:5000/app/hibernate + /employees | /projects | /roles