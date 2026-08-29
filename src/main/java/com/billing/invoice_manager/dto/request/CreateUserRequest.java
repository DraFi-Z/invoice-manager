package com.billing.invoice_manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "ADMIN|ACCOUNTANT|VIEWER", message = "Role must be ADMIN, ACCOUNTANT or VIEWER")
    private String role;

//    public CreateUserRequest() {
//    }
//
//    public String getFullName() { return this.fullName; }
//    public void setFullName(String fullName) { this.fullName = fullName; }
//
//    public String getEmail() { return this.email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return this.password; }
//    public void setPassword(String password) { this.password = password; }
//
//    public String getRole() { return this.role; }
//    public void setRole(String role) { this.role = role; }
}