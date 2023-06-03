package com.example.demoSpringVSHibernate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
public class RoleDTO {

    private Long id;
    @NotBlank
    private String name;
    private Set<Long> employeeIds;
    private Instant createdAt;
}
