package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.*;

public class BaseProjectControllerTest implements CrudTest {

    @Override
    public void testGet200(String url) {
        get(url + "/1")
                .then()
                .statusCode(SC_OK)
                .body("title", equalTo("project1"),
                        "createdAt", startsWith(DATE));

        get(url + "/2")
                .then()
                .statusCode(SC_OK)
                .body("title", equalTo("project2"),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testGet404(String url) {
        get(url + "/101")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Override
    public void testGetAll(String url) {
        get(url)
                .then()
                .statusCode(SC_OK)
                .body("title", hasItems("project1", "project2"),
                        "createdAt", everyItem(startsWith(DATE)));
    }

    @Override
    public void testPost200(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "newProject"
                        }
                        """)
                .post(url)
                .then()
                .statusCode(SC_OK)
                .body("title", equalTo("newProject"),
                        "employeeIds", empty(),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPost422(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": " "
                        }
                        """)
                .post(url)
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Override
    public void testPut200(String... urls) {
        setUpDataRoles(urls[0]);
        setUpDataEmployee(urls[1]);
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "project100",
                        "employeeIds": [1, 2]
                        }
                        """)
                .put(urls[2] + "/1")
                .then()
                .statusCode(SC_OK)
                .body("title", equalTo("project100"),
                        "employeeIds", contains(1, 2),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPut404(String... urls) {
        setUpDataRoles(urls[0]);
        setUpDataEmployee(urls[1]);
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "project2",
                        "employeeIds": [1]
                        }
                        """)
                .put(urls[2] + "/101")
                .then()
                .statusCode(SC_NOT_FOUND);

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "project2",
                        "employeeIds": [101]
                        }
                        """)
                .put(urls[2] + "/1")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Override
    public void testPut422(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "",
                        "employeeIds": [1]
                        }
                        """)
                .put(urls[1] + "/1")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }
}
