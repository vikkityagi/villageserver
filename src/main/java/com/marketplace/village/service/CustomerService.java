package com.marketplace.village.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marketplace.village.dto.CustomerDto;


public interface CustomerService {

    CustomerDto registerCustomer(CustomerDto customerDto);

    CustomerDto loginCustomer(String phone, String password);

    CustomerDto forgotPassword(String phone);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(UUID id);

    CustomerDto updateCustomer(UUID id, CustomerDto customerDto);

    void deleteCustomer(UUID id);
    
}
