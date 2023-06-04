package com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.utils.HibernateUtils;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.EmployeeService;
import com.example.demoSpringVSHibernate.service.ProjectService;
import com.example.demoSpringVSHibernate.service.RoleService;
import org.hibernate.Session;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public EmployeeDTO get(Long id) {
        return createEmployeeDTO(getEmployeeById(id));
    }

    @Override
    public Employee getEmployeeById(Long id) {
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
    public EmployeeDTO save(EmployeeDTO dto) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Employee employee = createEmployee(dto);
        session.persist(employee);
        HibernateUtils.commitTransactionAndCloseSession(session);
        return createEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO dto) {
        RoleServiceImpl roleService = new RoleServiceImpl();
        ProjectServiceImpl projectService = new ProjectServiceImpl();
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Employee employeeFromDB = getEmployeeById(id);
        employeeFromDB.setFirstname(dto.getFirstname());
        employeeFromDB.setRole(roleService.getRoleById(dto.getRoleId()));
        employeeFromDB.setLastname(dto.getLastname());
        employeeFromDB.setProjects(projectService.getProjectsByIds(dto.getProjectIds()));
        HibernateUtils.commitTransactionAndCloseSession(session);
        return createEmployeeDTO(employeeFromDB);
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Employee employee = getEmployeeById(id);
        session.remove(employee);
        HibernateUtils.commitTransactionAndCloseSession(session);
    }

    private Employee createEmployee(EmployeeDTO employeeDTO) {
        ProjectService projectService = new ProjectServiceImpl();
        RoleService roleService = new RoleServiceImpl();
        Role role = roleService.getRoleById(employeeDTO.getRoleId());
        Set<Project> projects = projectService.getProjectsByIds(employeeDTO.getProjectIds());
        return Employee.builder()
                .firstname(employeeDTO.getFirstname())
                .lastname(employeeDTO.getLastname())
                .role(role)
                .projects(projects)
                .build();
    }
}
