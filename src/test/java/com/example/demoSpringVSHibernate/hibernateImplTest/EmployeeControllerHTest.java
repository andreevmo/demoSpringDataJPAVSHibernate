package com.example.demoSpringVSHibernate.hibernateImplTest;

import com.example.demoSpringVSHibernate.BaseEmployeeControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-h.properties")
@Sql(value = "/delete.sql")
public class EmployeeControllerHTest extends BaseEmployeeControllerTest {

    @BeforeEach
    void beforeEach() {
        setUpData(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testGet() {
        testGet(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testGetAll() {
        testGetAll(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPost() {
        testPost(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPut() {
        testPut(FULL_URL_ROLES_H, FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testDelete() {
        testDelete(FULL_URL_EMPLOYEES_H);
    }
}