package com.example.demoSpringVSHibernate.DTO;

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
    private String firstname;
    private String lastname;
    private Long roleId;
    private Set<Long> projectIds;
    private Instant createdAt;
}
