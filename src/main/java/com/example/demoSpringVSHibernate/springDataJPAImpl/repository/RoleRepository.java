package com.example.demoSpringVSHibernate.springDataJPAImpl.repository;

import com.example.demoSpringVSHibernate.model.Role;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ListCrudRepository<Role, Long> {
}
