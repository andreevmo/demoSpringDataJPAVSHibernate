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
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public ProjectDTO get(Long id) {
        return createProjectDTO(getProjectById(id));
    }

    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("project with id " + id + " not found"));
    }

    @Override
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll().stream()
                .map(this::createProjectDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectDTO save(ProjectDTO dto) {
        return createProjectDTO(projectRepository.save(createProject(dto)));
    }

    @Override
    @Transactional
    public ProjectDTO update(Long id, ProjectDTO dto) {
        Project project = createProject(dto);
        project.setId(id);
        project.setCreatedAt(getProjectById(id).getCreatedAt());
        return createProjectDTO(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        getProjectById(id);
        projectRepository.deleteById(id);
    }

    private Project createProject(ProjectDTO projectDTO) {
        Set<Employee> employees = null;
        if (projectDTO.getEmployeeIds() != null) {
            employees = projectDTO.getEmployeeIds().stream()
                    .map(id -> employeeRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("employee with id " + id + " not found")))
                    .collect(Collectors.toSet());
        }
        return Project.builder()
                .title(projectDTO.getTitle())
                .employees(employees)
                .build();
    }
}
