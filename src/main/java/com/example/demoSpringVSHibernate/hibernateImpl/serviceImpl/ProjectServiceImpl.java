package com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.ProjectDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.utils.HibernateUtils;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;
import com.example.demoSpringVSHibernate.service.ProjectService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    EmployeeServiceImpl employeeService;

    @Override
    public ProjectDTO get(Long id) {
        return createProjectDTO(getById(id));
    }

    private Project getById(Long id) {
        if (id == null) {
            return null;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        Project project = session.get(Project.class, id);
        if (project == null) {
            throw new NoSuchElementException();
        }
        session.close();
        return project;
    }

    @Override
    public List<ProjectDTO> getAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Project> projects = session.createNativeQuery(
                        "SELECT * FROM project", Project.class)
                .getResultList();
        session.close();
        return projects.stream()
                .map(this::createProjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Project project = createProject(projectDTO);
        session.persist(project);
        transaction.commit();
        session.close();
        return createProjectDTO(project);
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTO projectDTO) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Project projectFromDB = session.get(Project.class, id);
        if (projectFromDB == null) {
            throw new NoSuchElementException();
        }
        projectFromDB.setEmployees(employeeService.getEmployeesByIds(projectDTO.getEmployeeIds()));
        projectFromDB.setTitle(projectDTO.getTitle());
        transaction.commit();
        session.close();
        return createProjectDTO(projectFromDB);
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Project project = session.get(Project.class, id);
        if (project == null) {
            throw new NoSuchElementException();
        }
        session.remove(project);
        transaction.commit();
        session.close();
    }

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        employeeService = new EmployeeServiceImpl();
        Set<Employee> employees = employeeService.getEmployeesByIds(projectDTO.getEmployeeIds());
        return Project.builder()
                .title(projectDTO.getTitle())
                .employees(employees)
                .build();
    }
}
