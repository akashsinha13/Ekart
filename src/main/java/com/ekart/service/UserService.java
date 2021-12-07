package com.ekart.service;

import com.ekart.dao.RoleRepository;
import com.ekart.dao.UserRepository;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.exception.UserAlreadyExistException;
import com.ekart.model.Address;
import com.ekart.model.Product;
import com.ekart.model.Role;
import com.ekart.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<User> userPage = userRepository.findAll(pageable);
        if (userPage.hasContent()) {
            return userPage.getContent();
        } else {
            return new ArrayList<User>();
        }
    }


    public User getUserById(Long id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No user exists for given id " + id);
        }
    }

    public User addUser(User user) throws UserAlreadyExistException {
        if (isEmailAlreadyExist(user.getEmail())) {
            throw new UserAlreadyExistException("Account already exist with given email: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user exists for given id " + id);
        }
    }

    private boolean isEmailAlreadyExist(String email) {

        return userRepository.findByEmail(email) != null;
    }

    public void addRole(Long id, String roleName) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RecordNotFoundException("No user exists with given id " + id);
        }

        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new RecordNotFoundException("No role exists with given name " + roleName);
        }

        user.get().getRoles().add(role.get());
    }

    public void addAddress(Long id, List<Address> addresses) throws RecordNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RecordNotFoundException("No user exists with given id " + id);
        }

        user.get().setAddresses(addresses);
    }
}
