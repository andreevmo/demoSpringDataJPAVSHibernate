package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Project;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface EmployeeService {

    EmployeeDTO get(Long id);

    List<EmployeeDTO> getAll();

    EmployeeDTO save(EmployeeDTO employeeDTO);

    EmployeeDTO update(Long id, EmployeeDTO employeeDTO);

    void delete(Long id);

    Employee createEmployee(EmployeeDTO employeeDTO);

    default EmployeeDTO createEmployeeDTO(Employee employee) {
        if (employee != null) {
            Long roleId = employee.getRole() == null ? null : employee.getRole().getId();
            return EmployeeDTO.builder()
                    .id(employee.getId())
                    .firstname(employee.getFirstname())
                    .lastname(employee.getLastname())
                    .roleId(roleId)
                    .projectIds(Optional.ofNullable(employee.getProjects())
                            .orElse(Set.of()).stream()
                            .filter(Objects::nonNull)
                            .map(Project::getId)
                            .collect(Collectors.toSet()))
                    .createdAt(employee.getCreatedAt())
                    .build();
        }
        return null;
    }
}
