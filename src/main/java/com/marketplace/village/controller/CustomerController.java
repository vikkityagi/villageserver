package com.marketplace.village.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.village.dto.CustomerDto;
import com.marketplace.village.entity.Customer;
import com.marketplace.village.repository.CustomerRepository;
import com.marketplace.village.service.CustomerService;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/login")
    public ResponseEntity<CustomerDto> loginCustomer(@RequestBody Customer customer) {

        try {
            CustomerDto customerDto = customerService.loginCustomer(customer.getCustomerPhone(),
                    customer.getPassword());
            return ResponseEntity.ok(customerDto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().build();

    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto customerDto) {
        try {
            customerDto = customerService.registerCustomer(customerDto);
            return ResponseEntity.ok(customerDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<CustomerDto> forgotPassword(@RequestBody String phone) {
        try {
            CustomerDto customerDto = customerService.forgotPassword(phone);
            return ResponseEntity.ok(customerDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        try {
            List<CustomerDto> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@RequestBody UUID id) {
        try {
            CustomerDto customerDto = customerService.getCustomerById(id);
            return ResponseEntity.ok(customerDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build(); 
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody UUID id, @RequestBody CustomerDto customerDto) {
        try {
            CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Void> deleteCustomer(@RequestBody UUID id) {
        try {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }
    
}
