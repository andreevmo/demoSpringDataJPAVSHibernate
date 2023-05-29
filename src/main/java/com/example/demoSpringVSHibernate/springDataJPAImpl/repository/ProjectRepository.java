package com.example.demoSpringVSHibernate.springDataJPAImpl.repository;

import com.example.demoSpringVSHibernate.model.Project;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {
}
