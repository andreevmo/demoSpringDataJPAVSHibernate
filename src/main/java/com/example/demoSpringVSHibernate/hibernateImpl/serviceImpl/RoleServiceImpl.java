package com.example.demoSpringVSHibernate.hibernateImpl.serviceImpl;

import com.example.demoSpringVSHibernate.DTO.RoleDTO;
import com.example.demoSpringVSHibernate.hibernateImpl.utils.HibernateUtils;
import com.example.demoSpringVSHibernate.model.Role;
import com.example.demoSpringVSHibernate.service.RoleService;
import org.hibernate.Session;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class RoleServiceImpl implements RoleService {

    @Override
    public RoleDTO get(Long id) {
        return createRoleDTO(getRoleById(id));
    }

    @Override
    public Role getRoleById(Long id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Role role = session.get(Role.class, id);
        if (role == null) {
            throw new NoSuchElementException("role with id " + id + " not found");
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
    public RoleDTO save(RoleDTO dto) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Role role = createRole(dto);
        session.persist(role);
        HibernateUtils.commitTransactionAndCloseSession(session);
        return createRoleDTO(role);
    }

    @Override
    public RoleDTO update(Long id, RoleDTO dto) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Role roleFromDB = getRoleById(id);
        roleFromDB.setName(dto.getName());
        HibernateUtils.commitTransactionAndCloseSession(session);
        return createRoleDTO(roleFromDB);
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateUtils.openSessionAndBeginTransaction();
        Role role = getRoleById(id);
        session.remove(role);
        HibernateUtils.commitTransactionAndCloseSession(session);
    }
}
