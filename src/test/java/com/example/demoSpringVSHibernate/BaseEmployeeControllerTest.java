package com.example.demoSpringVSHibernate;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.Matchers.*;

public class BaseEmployeeControllerTest implements CrudTest {

    @Override
    public void testGet200(String url) {
        get(url + "/1")
                .then()
                .statusCode(SC_OK)
                .body("firstname", equalTo("maxim"),
                        "lastname", equalTo("andreev"),
                        "roleId", equalTo(2),
                        "createdAt", containsString(DATE));

        get(url + "/2")
                .then()
                .statusCode(SC_OK)
                .body("firstname", equalTo("spring"),
                        "lastname", equalTo("hibernatov"),
                        "roleId", equalTo(1),
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
                .body("firstname", hasItems("maxim", "spring"),
                        "lastname", hasItems("andreev", "hibernatov"),
                        "createdAt", everyItem(startsWith(DATE)));
    }

    @Override
    public void testPost200(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "Ivan",
                        "lastname": "Ivanov",
                        "roleId": 1
                        }
                        """)
                .post(url)
                .then()
                .statusCode(SC_OK)
                .body("firstname", equalTo("Ivan"),
                        "lastname", equalTo("Ivanov"),
                        "roleId", equalTo(1),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPost422(String url) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "",
                        "lastname": "Ivanov"
                        }
                        """)
                .post(url)
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Override
    public void testPut200(String... urls) {
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
                .statusCode(SC_OK)
                .body("firstname", equalTo("Ivan"),
                        "lastname", equalTo("Ivanov"),
                        "roleId", equalTo(1),
                        "createdAt", startsWith(DATE));
    }

    @Override
    public void testPut404(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": "Ivan",
                        "lastname": "Ivanov",
                        "roleId": 1
                        }
                        """)
                .put(urls[1] + "/101")
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Override
    public void testPut422(String... urls) {
        with()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "firstname": " ",
                        "lastname": "Ivanov",
                        "roleId": 1
                        }
                        """)
                .put(urls[1] + "/1")
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }
}
