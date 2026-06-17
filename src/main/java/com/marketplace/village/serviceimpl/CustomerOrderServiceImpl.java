package com.marketplace.village.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.marketplace.village.dto.CustomerOrderDto;
import com.marketplace.village.entity.CustomerOrder;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopProduct;
import com.marketplace.village.exception.CustomException;
import com.marketplace.village.repository.CustomerOrderRepository;
import com.marketplace.village.repository.ShopProductRepository;
import com.marketplace.village.repository.ShopRepository;
import com.marketplace.village.service.CustomerOrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrderServiceImpl
        implements CustomerOrderService {

    private final CustomerOrderRepository repository;
    private final ShopRepository shopRepo;
    private final ShopProductRepository shopProductRepo;

    @Override
    public CustomerOrder placeOrder(CustomerOrderDto dto) {

        CustomerOrder order = new CustomerOrder();

        order.setCustomerName(dto.getCustomerName());
        order.setPhoneNumber(dto.getPhoneNumber());
        order.setPinCode(dto.getPinCode());
        order.setAddress(dto.getAddress());
        order.setLandmark(dto.getLandmark());
        order.setDeliveryOption(dto.getDeliveryOption());
        order.setQuantity(dto.getQuantity());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        order.setOrderDate(LocalDateTime.now());

        Optional<Shop> shopOpt = shopRepo.findById(dto.getShopId());
        if (!shopOpt.isPresent()) {
            throw new CustomException("Shop is not found");
        }
        order.setShopId(dto.getShopId());

        Optional<ShopProduct> shopProductOpt = shopProductRepo.findById(dto.getProductId());
        if (!shopProductOpt.isPresent()) {
            throw new CustomException("Shop Product is not found");
        }
        order.setProductId(dto.getProductId());

        int shopQuantity = shopProductOpt.get().getProductQuantity();
        int customerQuantity = dto.getQuantity();

        if (shopQuantity < customerQuantity) {
            throw new CustomException("Out of Stock");
        }

        shopProductOpt.get().setProductQuantity(shopQuantity - customerQuantity);

        shopProductRepo.save(shopProductOpt.get());

        return repository.save(order);
    }

    @Override
    public List<CustomerOrder> getAllOrders() {
        return repository.findAll();
    }

    @Override
    public CustomerOrder getOrderById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<CustomerOrderDto> getAllActiveOrders(String phoneNumber) {
        // TODO Auto-generated method stub
        List<CustomerOrder> customerOrderList = repository.findAllActiveOrderByPhoneNumber(phoneNumber);

        List<CustomerOrderDto> dtoList = customerOrderList.stream()
        .map(order -> {
            CustomerOrderDto dto = new CustomerOrderDto();
            dto.setId(order.getId());
            dto.setCustomerName(order.getCustomerName());
            dto.setPhoneNumber(order.getPhoneNumber());
            dto.setQuantity(order.getQuantity());
            dto.setAddress(order.getAddress());
            dto.setDeliveryOption(order.getDeliveryOption());
            dto.setLandmark(order.getLandmark());
            dto.setPinCode(order.getPinCode());
            dto.setOrderDate(order.getOrderDate());
            dto.setUpdatedAt(order.getUpdatedAt());
            dto.setIsActive(order.getIsActive());
            return dto;
        })
        .toList();

        return dtoList;
    }

    @Override
    public CustomerOrderDto changeStatus(UUID id, boolean status) {
        // TODO Auto-generated method stub
        Optional<CustomerOrder> orderOpt = repository.findById(id);

        if(!orderOpt.isPresent()){
            throw new CustomException("Order is not found");
        }

        CustomerOrder customerOrder = orderOpt.get();

        customerOrder.setIsActive(status);
        customerOrder.setUpdatedAt(LocalDateTime.now());

        customerOrder = repository.save(customerOrder);

        CustomerOrderDto dto = new CustomerOrderDto();

        dto.setIsActive(customerOrder.getIsActive());
        dto.setUpdatedAt(customerOrder.getUpdatedAt());

        return dto;
    }

    @Override
    public List<CustomerOrderDto> getOrderByShopId(UUID id) {
        // TODO Auto-generated method stub
        Optional<Shop> shopOpt = shopRepo.findById(id);

        if(!shopOpt.isPresent()){
            throw new CustomException("shop not found");
        }

        List<CustomerOrder> customerOrderList = repository.findOrderByShopId(id);

        List<CustomerOrderDto> dtoList = customerOrderList.stream()
        .map(order -> {
            CustomerOrderDto dto = new CustomerOrderDto();
            dto.setId(order.getId());
            dto.setCustomerName(order.getCustomerName());
            dto.setPhoneNumber(order.getPhoneNumber());
            dto.setQuantity(order.getQuantity());
            dto.setAddress(order.getAddress());
            dto.setDeliveryOption(order.getDeliveryOption());
            dto.setLandmark(order.getLandmark());
            dto.setPinCode(order.getPinCode());
            dto.setOrderDate(order.getOrderDate());
            dto.setUpdatedAt(order.getUpdatedAt());
            dto.setIsActive(order.getIsActive());
            return dto;
        })
        .toList();

        return dtoList;
    }
}