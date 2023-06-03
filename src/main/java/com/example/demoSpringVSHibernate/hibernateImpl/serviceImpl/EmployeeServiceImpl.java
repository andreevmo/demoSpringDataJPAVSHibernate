package com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.utils.HibernateUtils;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.EmployeeService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private RoleServiceImpl roleService;

    @Override
    public EmployeeDTO get(Long id) {
        return createEmployeeDTO(getById(id));
    }

    private Employee getById(Long id) {
        if (id == null) {
            return null;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        if (employee == null) {
            throw new NoSuchElementException("employee with id " + id + " not found");
        }
        session.close();
        return employee;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Employee> employees = session.createNativeQuery(
                        "SELECT * FROM employee", Employee.class)
                .getResultList();
        session.close();
        return employees.stream()
                .map(this::createEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = createEmployee(employeeDTO);
        session.persist(employee);
        transaction.commit();
        session.close();
        return createEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {
        roleService = new RoleServiceImpl();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Employee employeeFromDB = session.get(Employee.class, id);
        if (employeeFromDB == null) {
            throw new NoSuchElementException("employee with id " + id + " not found");
        }
        employeeFromDB.setFirstname(employeeDTO.getFirstname());
        employeeFromDB.setRole(roleService.getById(employeeDTO.getRoleId()));
        employeeFromDB.setLastname(employeeDTO.getLastname());
        transaction.commit();
        session.close();
        return createEmployeeDTO(employeeFromDB);
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        if (employee == null) {
            throw new NoSuchElementException("employee with id " + id + " not found");
        }
        session.remove(employee);
        transaction.commit();
        session.close();
    }

    @Override
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        roleService = new RoleServiceImpl();
        Role role = roleService.getById(employeeDTO.getRoleId());
        return Employee.builder()
                .firstname(employeeDTO.getFirstname())
                .lastname(employeeDTO.getLastname())
                .role(role)
                .build();
    }

    public Set<Employee> getEmployeesByIds(Set<Long> ids) {
        Set<Employee> employees = new HashSet<>();
        if (ids != null) {
            ids.forEach(id -> employees.add(getById(id)));
        }
        return employees;
    }
}
