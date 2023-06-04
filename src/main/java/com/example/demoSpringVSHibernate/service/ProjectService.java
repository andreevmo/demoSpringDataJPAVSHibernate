package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.DTO.ProjectDTO;
import com.example.demoSpringVSHibernate.model.Project;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface ProjectService extends BaseService<ProjectDTO> {

    default ProjectDTO createProjectDTO(Project project) {
        if (project != null) {
            return ProjectDTO.builder()
                    .id(project.getId())
                    .title(project.getTitle())
                    .employeeIds(getIds(project.getEmployees()))
                    .createdAt(project.getCreatedAt())
                    .build();
        }
        return null;
    }

    Project getProjectById(Long id);

    default Set<Project> getProjectsByIds(Set<Long> ids) {
        return Optional.ofNullable(ids)
                .orElse(Set.of()).stream()
                .map(this::getProjectById)
                .collect(Collectors.toSet());
    }
}
