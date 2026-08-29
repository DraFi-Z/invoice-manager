package com.billing.invoice_manager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateCustomerRequest {

    @NotBlank(message = "Customer name is required")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @Size(max = 50, message = "Phone cannot exceed 50 characters")
    private String phone;

    @Size(max = 255, message = "Address line 1 cannot exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 cannot exceed 255 characters")
    private String addressLine2;

    @Size(max = 100, message = "City cannot exceed 100 characters")
    private String city;

    @Size(max = 100, message = "Country cannot exceed 100 characters")
    private String country;

//    public CreateCustomerRequest() {
//    }
//
//    public String getName() { return this.name; }
//    public void setName(String name) { this.name = name; }
//
//    public String getEmail() { return this.email; }
//    public void setEmail(String email) { this.email = email; }
//
//    public String getPhone() { return this.phone; }
//    public void setPhone(String phone) { this.phone = phone; }
//
//    public String getAddressLine1() { return this.addressLine1; }
//    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
//
//    public String getAddressLine2() { return this.addressLine2; }
//    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
//
//    public String getCity() { return this.city; }
//    public void setCity(String city) { this.city = city; }
//
//    public String getCountry() { return this.country; }
//    public void setCountry(String country) { this.country = country; }
}
