package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.model.Role;

public interface RoleService extends BaseService<RoleDTO> {

    default RoleDTO createRoleDTO(Role role) {
        if (role != null) {
            return RoleDTO.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .employeeIds(getIds(role.getEmployees()))
                    .createdAt(role.getCreatedAt())
                    .build();
        }
        return null;
    }

    Role getRoleById(Long id);

    default Role createRole(RoleDTO roleDTO) {
        return Role.builder()
                .name(roleDTO.getName())
                .build();
    }
}
