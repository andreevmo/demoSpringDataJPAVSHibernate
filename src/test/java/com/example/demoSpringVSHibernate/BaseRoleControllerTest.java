package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BaseRoleControllerTest implements CrudTest {

    public void setUpData(String url) {
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

    @Override
    public void testGet(String url) {
        get(url + "/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("user"),
                        "createdAt", startsWith(DATE));

        get(url + "/2")
                .then()
                .statusCode(200)
                .body("name", equalTo("admin"),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testGetAll(String url) {
        get(url)
                .then()
                .statusCode(200)
                .body("name", hasItems("user", "admin"),
                        "createdAt", everyItem(startsWith(DATE)));
    }

    @Override
    public void testPost(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "QA"
                        }
                        """)
                .post(url)
                .then()
                .statusCode(200)
                .body("name", equalTo("QA"),
                        "employeeIds", empty(),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPut(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "guest"
                        }
                        """)
                .put(urls[0] + "/1")
                .then()
                .statusCode(200)
                .body("name", equalTo("guest"),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testDelete(String url) {
        delete(url + "/1")
                .then()
                .statusCode(200);

        get(url + "/1")
                .then()
                .statusCode(404);
    }
}
