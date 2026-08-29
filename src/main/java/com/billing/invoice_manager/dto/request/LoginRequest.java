package com.billing.invoice_manager.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

//    public LoginRequest() {
//    }
//
//    public String getEmail() { return this.email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPassword() { return this.password; }
//    public void setPassword(String password) { this.password = password; }
}