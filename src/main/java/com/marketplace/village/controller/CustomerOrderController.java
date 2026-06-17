package com.marketplace.village.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.marketplace.village.dto.CustomerOrderDto;
import com.marketplace.village.entity.CustomerOrder;
import com.marketplace.village.service.CustomerOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
public class CustomerOrderController {

    private final CustomerOrderService service;

    @PostMapping("/place")
    public ResponseEntity<CustomerOrder> placeOrder(
            @Valid @RequestBody CustomerOrderDto dto) {

        return ResponseEntity.ok(
                service.placeOrder(dto));
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {

        return ResponseEntity.ok(
                service.getAllOrders());
    }

    @GetMapping("/phone_number")
    public ResponseEntity<List<CustomerOrderDto>> getAllActiveOrdersByPhoneNumber(
            @RequestParam("phone_number") String phoneNumber) {

        return ResponseEntity.ok(
                service.getAllActiveOrders(phoneNumber));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerOrder> getById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                service.getOrderById(id));
    }

    @PatchMapping("/change_status/{id}")
    public ResponseEntity<CustomerOrderDto> changeStatus(
            @PathVariable UUID id, @RequestParam("status") boolean status) {
        CustomerOrderDto dto = service.changeStatus(id, status);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/by_shop_id/{id}")
    public ResponseEntity<List<CustomerOrderDto>> changeStatus(
            @PathVariable UUID id) {
        List<CustomerOrderDto> dto = service.getOrderByShopId(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}