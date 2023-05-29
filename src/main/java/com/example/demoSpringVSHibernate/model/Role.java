package com.example.demoSpringVSHibernate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseEntity {

    private String name;
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<Employee> employees;
}
