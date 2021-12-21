package com.ekart.controller;

import com.ekart.dto.ProductDto;
import com.ekart.dto.UserDto;
import com.ekart.exception.RecordNotFoundException;
import com.ekart.exception.UserAlreadyExistException;
import com.ekart.model.Address;
import com.ekart.model.Product;
import com.ekart.model.User;
import com.ekart.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${apiPrefix}/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     @RequestParam(defaultValue = "name") String sortBy) {
        List<User> users = userService.getAllUsers(pageNo, pageSize, sortBy);
        List<UserDto> userResponse = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<List<UserDto>>(userResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws RecordNotFoundException {
        User user = userService.getUserById(id);
        return new ResponseEntity<User>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public HttpStatus addUser(@RequestBody Map<String, Object> request) throws UserAlreadyExistException {
        userService.addUser(request);
        return HttpStatus.CREATED;
    }

    @DeleteMapping(path = "/{id}")
    public HttpStatus deleteUserById(@PathVariable Long id) throws RecordNotFoundException {
        userService.deleteUserById(id);
        return HttpStatus.FORBIDDEN;
    }

    @PutMapping(path = "/{id}/role")
    public void addRoleToUser(@PathVariable Long id, @RequestBody String role) throws RecordNotFoundException {
        userService.addRole(id, role);
    }

    @PutMapping(path = "/{id}/address")
    public void addAddressToUser(@PathVariable Long id, @RequestBody List<Address> address) throws RecordNotFoundException {
        userService.addAddress(id, address);
    }
}
