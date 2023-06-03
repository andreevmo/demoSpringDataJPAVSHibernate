package com.example.demoSpringVSHibernate.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
public class EmployeeDTO {

    private Long id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotNull
    private Long roleId;
    private Set<Long> projectIds;
    private Instant createdAt;
}
