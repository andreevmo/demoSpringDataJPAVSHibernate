package com.example.demoSpringVSHibernate.springDataJPAImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.ProjectDTO;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;
import com.example.demoSpringVSHibernate.service.ProjectService;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.EmployeeRepository;
import com.example.demoSpringVSHibernate.springDataJPAImpl.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public ProjectDTO get(Long id) {
        return createProjectDTO(projectRepository.findById(id).orElseThrow());
    }

    @Override
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll().stream()
                .map(this::createProjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDTO save(ProjectDTO projectDTO) {
        return createProjectDTO(projectRepository.save(createProject(projectDTO)));
    }

    @Override
    @Transactional
    public ProjectDTO update(Long id, ProjectDTO projectDTO) {
        Project projectFromDB = projectRepository.findById(id).orElseThrow();
        Project project = createProject(projectDTO);
        project.setId(id);
        project.setCreatedAt(projectFromDB.getCreatedAt());
        return createProjectDTO(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        projectRepository.findById(id).orElseThrow();
        projectRepository.deleteById(id);
    }

    @Override
    public Project createProject(ProjectDTO projectDTO) {
        Set<Employee> employees = projectDTO.getEmployeeIds() != null
                ? employeeRepository.findEmployeesByIdIn(projectDTO.getEmployeeIds()) : null;
        return Project.builder()
                .title(projectDTO.getTitle())
                .employees(employees)
                .build();
    }
}
