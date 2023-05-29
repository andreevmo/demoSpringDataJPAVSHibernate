package com.example.demoSpringVSHibernate.springDataJPAImpl.repository;

import com.example.demoSpringVSHibernate.model.Employee;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EmployeeRepository extends ListCrudRepository<Employee, Long> {
    /*
    Example create queries:

        List<Employee> findEmployeeByRole(Role role);
        List<Employee> findEmployeeByIdNot(Long id);
        List<Employee> findEmployeeByFirstnameContaining(String containFirstname);

        @Query(value = "SELECT * FROM EMPLOYEES WHERE FIRSTNAME = ?0 AND LASTNAME = ?1", nativeQuery = true)
        Employee findByFirstnameAndLastname(String firstname, String lastname);

    */
    Set<Employee> findEmployeesByIdIn(Set<Long> ids);

}