package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.DTO.EmployeeDTO;
import com.example.demoSpringVSHibernate.model.Employee;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface EmployeeService extends BaseService<EmployeeDTO> {

    default EmployeeDTO createEmployeeDTO(Employee employee) {
        if (employee != null) {
            Long roleId = employee.getRole() == null ? null : employee.getRole().getId();
            return EmployeeDTO.builder()
                    .id(employee.getId())
                    .firstname(employee.getFirstname())
                    .lastname(employee.getLastname())
                    .roleId(roleId)
                    .projectIds(getIds(employee.getProjects()))
                    .createdAt(employee.getCreatedAt())
                    .build();
        }
        return null;
    }

    Employee getEmployeeById(Long id);

    default Set<Employee> getEmployeesByIds(Set<Long> ids) {
        return Optional.ofNullable(ids)
                .orElse(Set.of()).stream()
                .map(this::getEmployeeById)
                .collect(Collectors.toSet());
    }
}
