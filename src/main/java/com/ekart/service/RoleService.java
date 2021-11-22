package com.ekart.service;

import com.ekart.dao.RoleRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.size() > 0 ? roles : new ArrayList<Role>();
    }

    public Role getRoleById(Long id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RecordNotFoundException("No role exists for given id " + id);
        }
    }

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRoleById(Long id) throws RecordNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            roleRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No role exists for given id " + id);
        }
    }
}
