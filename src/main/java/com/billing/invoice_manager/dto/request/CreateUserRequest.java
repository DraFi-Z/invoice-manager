package com.billing.invoice_manager.dto.request;

public class CreateUserRequest {

    private String fullName;

    private String email;

    private String password;

    private String role;

    public CreateUserRequest() {
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public void setRole(String role){
        this.role=role;
    }

    public String getEmail(){
        return this.email;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRole(){
        return this.role;
    }
}