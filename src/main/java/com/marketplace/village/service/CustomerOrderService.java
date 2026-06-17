package com.marketplace.village.service;

import java.util.List;
import java.util.UUID;

import com.marketplace.village.dto.CustomerOrderDto;
import com.marketplace.village.entity.CustomerOrder;

public interface CustomerOrderService {

    CustomerOrder placeOrder(CustomerOrderDto dto);

    List<CustomerOrder> getAllOrders();

    CustomerOrder getOrderById(UUID id);

    List<CustomerOrderDto> getOrderByShopId(UUID id);

    List<CustomerOrderDto> getAllActiveOrders(String phoneNumber);

    CustomerOrderDto changeStatus(UUID id,boolean status);
}