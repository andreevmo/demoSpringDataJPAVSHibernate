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
        setUpDataRoles(FULL_URL_ROLES_H);
        setUpDataEmployee(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testGet200() {
        testGet200(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testGet404() {
        testGet404(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testGetAll() {
        testGetAll(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPost200() {
        testPost200(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPost422() {
        testPost422(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPut200() {
        testPut200(FULL_URL_ROLES_H, FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPut404() {
        testPut404(FULL_URL_ROLES_H, FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testPut422() {
        testPut422(FULL_URL_ROLES_H, FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testDelete200() {
        testDelete200(FULL_URL_EMPLOYEES_H);
    }

    @Test
    public void testDelete404() {
        testDelete404(FULL_URL_EMPLOYEES_H);
    }
}
