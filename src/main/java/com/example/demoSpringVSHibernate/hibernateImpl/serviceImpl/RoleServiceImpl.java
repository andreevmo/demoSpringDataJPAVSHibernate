package com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.utils.HibernateUtils;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.RoleService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RoleServiceImpl implements RoleService {

    @Override
    public RoleDTO get(Long id) {
        return createRoleDTO(getById(id));
    }

    public Role getById(Long id) {
        if (id == null) {
            return null;
        }
        Session session = HibernateUtils.getSessionFactory().openSession();
        Role role = session.get(Role.class, id);
        if (role == null) {
            throw new NoSuchElementException();
        }
        session.close();
        return role;
    }

    @Override
    public List<RoleDTO> getAll() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Role> roles = session.createNativeQuery(
                        "SELECT * FROM role", Role.class)
                .getResultList();
        session.close();
        return roles.stream()
                .map(this::createRoleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Role role = createRole(roleDTO);
        session.persist(role);
        transaction.commit();
        session.close();
        return createRoleDTO(role);
    }

    @Override
    public RoleDTO update(Long id, RoleDTO roleDTO) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Role roleFromDB = session.get(Role.class, id);
        if (roleFromDB == null) {
            throw new NoSuchElementException();
        }
        roleFromDB.setName(roleDTO.getName());
        transaction.commit();
        session.close();
        return createRoleDTO(roleFromDB);
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Role role = session.get(Role.class, id);
        if (role == null) {
            throw new NoSuchElementException();
        }
        session.remove(role);
        transaction.commit();
        session.close();
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        return Role.builder()
                .name(roleDTO.getName())
                .build();
    }
}
