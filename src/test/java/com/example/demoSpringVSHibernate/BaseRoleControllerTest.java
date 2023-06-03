package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.hamcrest.Matchers.*;

public class BaseRoleControllerTest implements CrudTest {

    @Override
    public void testGet200(String url) {
        get(url + "/1")
                .then()
                .statusCode(SC_OK)
                .body("name", equalTo("user"),
                        "createdAt", startsWith(DATE));

        get(url + "/2")
                .then()
                .statusCode(SC_OK)
                .body("name", equalTo("admin"),
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
                .body("name", hasItems("user", "admin"),
                        "createdAt", everyItem(startsWith(DATE)));
    }

    @Override
    public void testPost200(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "QA"
                        }
                        """)
                .post(url)
                .then()
                .statusCode(SC_OK)
                .body("name", equalTo("QA"),
                        "employeeIds", empty(),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPost422(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": " "
                        }
                        """)
                .post(url)
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY);
    }

    @Override
    public void testPut200(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "guest"
                        }
                        """)
                .put(urls[0] + "/1")
                .then()
                .statusCode(SC_OK)
                .body("name", equalTo("guest"),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPut404(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "guest"
                        }
                        """)
                .put(urls[0] + "/101")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Override
    public void testPut422(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": " "
                        }
                        """)
                .put(urls[0] + "/1")
                .then()
                .statusCode(SC_UNPROCESSABLE_ENTITY);
    }
}
