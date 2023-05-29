package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.DTO.ProjectDTO;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface ProjectService {

    ProjectDTO get(Long id);

    List<ProjectDTO> getAll();

    ProjectDTO save(ProjectDTO role);

    ProjectDTO update(Long id, ProjectDTO projectDTO);

    void delete(Long id);

    Project createProject(ProjectDTO projectDTO);

    default ProjectDTO createProjectDTO(Project project) {
        if (project != null) {
            return ProjectDTO.builder()
                    .id(project.getId())
                    .title(project.getTitle())
                    .employeeIds(Optional.ofNullable(project.getEmployees())
                            .orElse(Set.of()).stream()
                            .filter(Objects::nonNull)
                            .map(Employee::getId)
                            .collect(Collectors.toSet()))
                    .createdAt(project.getCreatedAt())
                    .build();
        }
        return null;
    }
}
