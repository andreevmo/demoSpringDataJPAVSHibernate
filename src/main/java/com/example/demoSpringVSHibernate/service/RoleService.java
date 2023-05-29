package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.model.Employee;
import com.example.demoSpringVSHibernate.model.Role;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface RoleService {

    RoleDTO get(Long id);

    List<RoleDTO> getAll();

    RoleDTO save(RoleDTO roleDTO);

    RoleDTO update(Long id, RoleDTO roleDTO);

    void delete(Long id);

    Role createRole(RoleDTO roleDTO);

    default RoleDTO createRoleDTO(Role role) {
        if (role != null) {
            return RoleDTO.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .employeeIds(Optional.ofNullable(role.getEmployees())
                            .orElse(Set.of()).stream()
                            .filter(Objects::nonNull)
                            .map(Employee::getId)
                            .collect(Collectors.toSet()))
                    .createdAt(role.getCreatedAt())
                    .build();
        }
        return null;
    }
}
