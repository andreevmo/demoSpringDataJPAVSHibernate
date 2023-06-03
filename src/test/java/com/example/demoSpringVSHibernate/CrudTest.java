package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.EmployeeController.EMPLOYEE_CONTROLLER_PATH;
import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.ProjectController.PROJECT_CONTROLLER_PATH;
import static com.example.demoSpringVSHibernate.springDataJPAImpl.controller.RoleController.ROLES_CONTROLLER_PATH;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.with;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;

public interface CrudTest {

    String TEST_PATH_SPRING = "http://localhost:5000/api/spring";
    String TEST_PATH_HIBERNATE = "http://localhost:5000/api/hibernate";
    String FULL_URL_EMPLOYEES_SPRING = TEST_PATH_SPRING + EMPLOYEE_CONTROLLER_PATH;
    String FULL_URL_EMPLOYEES_H = TEST_PATH_HIBERNATE + EMPLOYEE_CONTROLLER_PATH;
    String FULL_URL_ROLES_SPRING = TEST_PATH_SPRING + ROLES_CONTROLLER_PATH;
    String FULL_URL_ROLES_H = TEST_PATH_HIBERNATE + ROLES_CONTROLLER_PATH;
    String FULL_URL_PROJECTS_SPRING = TEST_PATH_SPRING + PROJECT_CONTROLLER_PATH;
    String FULL_URL_PROJECTS_H = TEST_PATH_HIBERNATE + PROJECT_CONTROLLER_PATH;
    String DATE = LocalDateTime.now().minusHours(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    default void setUpDataRoles(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "user"
                        }
                        """)
                .post(url)
                .body();

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "admin"
                        }
                        """)
                .post(url)
                .body();
    }

    default void setUpDataProjects(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "project1"
                        }
                        """)
                .post(url)
                .body();

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "project2"
                        }
                        """)
                .post(url)
                .body();
    }

    default void setUpDataEmployee(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "maxim",
                        "lastname": "andreev",
                        "roleId": 2
                        }
                        """)
                .post(url)
                .body();

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "spring",
                        "lastname": "hibernatov",
                        "roleId": 1
                        }
                        """)
                .post(url)
                .body();
    }

    void testGet200(String url);
    void testGet404(String url);

    void testGetAll(String url);

    void testPost200(String url);
    void testPost422(String url);

    void testPut200(String... urls);
    void testPut404(String... urls);
    void testPut422(String... urls);

    default void testDelete200(String url) {
        delete(url + "/1")
                .then()
                .statusCode(SC_OK);

        get(url + "/1")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    default void testDelete404(String url) {
        delete(url + "/101")
                .then()
                .statusCode(SC_NOT_FOUND);
    }
}
