//package com.billing.invoice_manager.controller;
//
//import com.billing.invoice_manager.entity.User;
//import com.billing.invoice_manager.exception.ResourceNotFoundException;
//import com.billing.invoice_manager.service.UserService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User created = userService.createUser(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers() {
//        List<User> users = userService.getAllUsers();
//        return ResponseEntity.ok(users);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        User user = userService.getUserById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
//        return ResponseEntity.ok(user);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id,
//                                                   @RequestBody User user) {
//        User updated = userService.updateUser(id, user);
//        return ResponseEntity.ok(updated);
//    }
//
//    @PutMapping("/{id}/deactivate")
//    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
//        userService.deactivateUser(id);
//        return ResponseEntity.noContent().build();
//    }
//}



package com.billing.invoice_manager.controller;

import com.billing.invoice_manager.dto.request.CreateUserRequest;
import com.billing.invoice_manager.dto.response.UserResponse;
import com.billing.invoice_manager.entity.User;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.mapper.UserMapper;
import com.billing.invoice_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = UserMapper.INSTANCE.toEntity(request);
        User created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.INSTANCE.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> UserMapper.INSTANCE.toResponse(user))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                   @Valid @RequestBody CreateUserRequest request) {
        User user = UserMapper.INSTANCE.toEntity(request);
        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(UserMapper.INSTANCE.toResponse(updated));
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }
}