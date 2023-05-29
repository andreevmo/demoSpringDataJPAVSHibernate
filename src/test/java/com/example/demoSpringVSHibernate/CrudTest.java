package com.example.demoSpringVSHibernate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.EmployeeController.EMPLOYEE_CONTROLLER_PATH;
import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.ProjectController.PROJECT_CONTROLLER_PATH;
import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.RoleController.ROLES_CONTROLLER_PATH;

public interface CrudTest {

    String TEST_PATH_SPRING = "http://localhost:5000/app/spring";
    String TEST_PATH_HIBERNATE = "http://localhost:5000/app/hibernate";
    String FULL_URL_EMPLOYEES_SPRING = TEST_PATH_SPRING + EMPLOYEE_CONTROLLER_PATH;
    String FULL_URL_EMPLOYEES_H = TEST_PATH_HIBERNATE + EMPLOYEE_CONTROLLER_PATH;
    String FULL_URL_ROLES_SPRING = TEST_PATH_SPRING + ROLES_CONTROLLER_PATH;
    String FULL_URL_ROLES_H = TEST_PATH_HIBERNATE + ROLES_CONTROLLER_PATH;
    String FULL_URL_PROJECTS_SPRING = TEST_PATH_SPRING + PROJECT_CONTROLLER_PATH;
    String FULL_URL_PROJECTS_H = TEST_PATH_HIBERNATE + PROJECT_CONTROLLER_PATH;
    String DATE = LocalDateTime.now().minusHours(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    void testGet(String url);

    void testGetAll(String url);

    void testPost(String url);

    void testPut(String... urls);

    void testDelete(String url);
}
