package com.example.demoSpringVSHibernate.service;

import com.example.demoSpringVSHibernate.model.BaseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface BaseService<T> {

    T get(Long id);

    List<T> getAll();

    T save(T dto);

    T update(Long id, T dto);

    void delete(Long id);

    default Set<Long> getIds(Set<? extends BaseEntity> entities) {
        return Optional.ofNullable(entities)
                .orElse(Set.of()).stream()
                .filter(Objects::nonNull)
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }
}
