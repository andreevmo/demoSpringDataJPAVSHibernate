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
public class ProjectDTO {

    private Long id;
    @NotBlank
    private String title;
    private Set<Long> employeeIds;
    private Instant createdAt;
}
