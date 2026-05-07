package com.billing.invoice_manager.mapper;

import com.billing.invoice_manager.dto.request.CreateUserRequest;
import com.billing.invoice_manager.dto.response.UserResponse;
import com.billing.invoice_manager.entity.User;

public class UserMapper {

    public static User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword());
        user.setRole(request.getRole());
        return user;
    }

    public static UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setIsActive(user.getIsActive());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}