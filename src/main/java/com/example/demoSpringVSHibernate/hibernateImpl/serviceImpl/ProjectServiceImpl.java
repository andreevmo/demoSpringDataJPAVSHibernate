package com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.ProjectDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.utils.HibernateUtils;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;
import com.example.demoSpringVSHibernate.service.EmployeeService;
import com.example.demoSpringVSHibernate.service.ProjectService;
import org.hibernate.Session;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    @Override
    public ProjectDTO get(Long id) {
        return createProjectDTO(getProjectById(id));
    }

    @Override
    public Project getProjectById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Project project = session.get(Project.class, id);
        if (project == null) {
            throw new NoSuchElementException("project with id " + id + " not found");
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
    public ProjectDTO save(ProjectDTO dto) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Project project = createProject(dto);
        session.persist(project);
        HibernateUtils.commitTransactionAndCloseSession(session);
        return createProjectDTO(project);
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTO dto) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Project projectFromDB = getProjectById(id);
        projectFromDB.setEmployees(new EmployeeServiceImpl().getEmployeesByIds(dto.getEmployeeIds()));
        projectFromDB.setTitle(dto.getTitle());
        HibernateUtils.commitTransactionAndCloseSession(session);
        return createProjectDTO(projectFromDB);
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Project project = getProjectById(id);
        session.remove(project);
        HibernateUtils.commitTransactionAndCloseSession(session);
    }

    private Project createProject(ProjectDTO projectDTO) {
        EmployeeService employeeService = new EmployeeServiceImpl();
        Set<Employee> employees = null;
        if (projectDTO.getEmployeeIds() != null) {
            employees = projectDTO.getEmployeeIds().stream()
                    .map(employeeService::getEmployeeById)
                    .collect(Collectors.toSet());
        }
        return Project.builder()
                .title(projectDTO.getTitle())
                .employees(employees)
                .build();
    }
}
