package com.ekart.controller;

import com.ekart.exception.RecordNotFoundException;
import com.ekart.model.Role;
import com.ekart.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<List<Role>>(roles, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) throws RecordNotFoundException {
        Role role = roleService.getRoleById(id);
        return new ResponseEntity<Role>(role, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        Role newRole = roleService.addRole(role);
        return new ResponseEntity<Role>(newRole, new HttpHeaders(), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteRoleById(@PathVariable Long id) throws RecordNotFoundException {
        roleService.deleteRoleById(id);
        return HttpStatus.FORBIDDEN;
    }
}
