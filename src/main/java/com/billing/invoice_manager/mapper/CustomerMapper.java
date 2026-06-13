//package com.billing.invoice_manager.mapper;
//
//import com.billing.invoice_manager.dto.request.CreateCustomerRequest;
//import com.billing.invoice_manager.dto.response.CustomerResponse;
//import com.billing.invoice_manager.entity.Customer;
//
//public class CustomerMapper {
//
//    public static Customer toEntity(CreateCustomerRequest request) {
//        Customer customer = new Customer();
//        customer.setName(request.getName());
//        customer.setEmail(request.getEmail());
//        customer.setPhone(request.getPhone());
//        customer.setAddressLine1(request.getAddressLine1());
//        customer.setAddressLine2(request.getAddressLine2());
//        customer.setCity(request.getCity());
//        customer.setCountry(request.getCountry());
//        return customer;
//    }
//
//    public static CustomerResponse toResponse(Customer customer) {
//        CustomerResponse response = new CustomerResponse();
//        response.setId(customer.getId());
//        response.setName(customer.getName());
//        response.setEmail(customer.getEmail());
//        response.setPhone(customer.getPhone());
//        response.setAddressLine1(customer.getAddressLine1());
//        response.setAddressLine2(customer.getAddressLine2());
//        response.setCity(customer.getCity());
//        response.setCountry(customer.getCountry());
//        response.setCreatedAt(customer.getCreatedAt());
//        return response;
//    }
//}


package com.billing.invoice_manager.mapper;

import com.billing.invoice_manager.dto.request.CreateCustomerRequest;
import com.billing.invoice_manager.dto.response.CustomerResponse;
import com.billing.invoice_manager.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toEntity(CreateCustomerRequest request);

    CustomerResponse toResponse(Customer customer);
}