package com.example.demoSpringVSHibernate.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee extends BaseEntity {

    private String firstname;
    private String lastname;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER)
    private Set<Project> projects;
}
