package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BaseProjectControllerTest implements CrudTest {

    public void setUpData(String url) {
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

    @Override
    public void testGet(String url) {
        get(url + "/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("project1"),
                        "createdAt", startsWith(DATE));

        get(url + "/2")
                .then()
                .statusCode(200)
                .body("title", equalTo("project2"),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testGetAll(String url) {
        get(url)
                .then()
                .statusCode(200)
                .body("title", hasItems("project1", "project2"),
                        "createdAt", everyItem(startsWith(DATE)));
    }

    @Override
    public void testPost(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "newProject"
                        }
                        """)
                .post(url)
                .then()
                .statusCode(200)
                .body("title", equalTo("newProject"),
                        "employeeIds", empty(),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPut(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "Admin",
                        "lastname": "Adminov"
                        }
                        """)
                .post(urls[0]);

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "title": "project2",
                        "employeeIds": [1]
                        }
                        """)
                .put(urls[1] + "/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("project2"),
                        "employeeIds", contains(1),
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
