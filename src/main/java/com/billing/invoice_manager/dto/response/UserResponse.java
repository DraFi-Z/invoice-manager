package com.billing.invoice_manager.dto.response;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String role;

    private Boolean isActive ;

    private LocalDateTime createdAt;

    public UserResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setFullName(String fullName){
        this.fullName=fullName;
    }

    public void setRole(String role){
        this.role=role;
    }

    public void setIsActive(Boolean isActive){
        this.isActive=isActive;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }

    public Long getId(){
        return this.id;
    }

    public String getEmail(){
        return this.email;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;

    }
    public String getFullName(){
        return this.fullName;
    }

    public Boolean getIsActive(){
        return this.isActive;
    }

    public String getRole(){
        return this.role;
    }
}
