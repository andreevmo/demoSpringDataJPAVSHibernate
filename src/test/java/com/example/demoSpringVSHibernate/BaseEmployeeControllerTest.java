package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BaseEmployeeControllerTest implements CrudTest {

    public void setUpData(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "maxim",
                        "lastname": "andreev"
                        }
                        """)
                .post(url)
                .body();

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "spring",
                        "lastname": "hibernatov"
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
                .body("firstname", equalTo("maxim"),
                        "lastname", equalTo("andreev"),
                        "createdAt", containsString(DATE));

        get(url + "/2")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("spring"),
                        "lastname", equalTo("hibernatov"),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testGetAll(String url) {
        get(url)
                .then()
                .statusCode(200)
                .body("firstname", hasItems("maxim", "spring"),
                        "lastname", hasItems("andreev", "hibernatov"),
                        "createdAt", everyItem(startsWith(DATE)));
    }

    @Override
    public void testPost(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "Ivan",
                        "lastname": "Ivanov"
                        }
                        """)
                .post(url)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Ivan"),
                        "lastname", equalTo("Ivanov"),
                        "roleId", equalTo(null),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPut(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "name": "Admin"
                        }
                        """)
                .post(urls[0]);

        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "Ivan",
                        "lastname": "Ivanov",
                        "roleId": 1
                        }
                        """)
                .put(urls[1] + "/1")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Ivan"),
                        "lastname", equalTo("Ivanov"),
                        "roleId", equalTo(1),
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
