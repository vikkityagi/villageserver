package com.marketplace.village.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.village.dto.OrderItemDto;
import com.marketplace.village.dto.OrderRequest;
import com.marketplace.village.entity.Order;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopProduct;
import com.marketplace.village.enums.DeliveryType;
import com.marketplace.village.exception.CustomException;
import com.marketplace.village.repository.OrderRepository;
import com.marketplace.village.repository.ShopProductRepository;
import com.marketplace.village.repository.ShopRepository;
import com.marketplace.village.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShopRepository shopRepo;

    @Autowired
    private ShopProductRepository spRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public Object placeOrder(OrderRequest req) {

        // Shop shop = shopRepo.findById(req.getShopId())
        // .orElseThrow(() -> new CustomException("Shop not found"));

        // if (!shop.getIsActive()) {
        // throw new CustomException("Shop is currently closed");
        // }

        // if ("DELIVERY".equals(req.getDeliveryType()) && !shop.getDeliveryAvailable())
        // {
        // throw new CustomException("Delivery not available for this shop");
        // }

        // if ("DELIVERY".equals(req.getDeliveryType()) &&
        // (req.getCustomerAddress() == null || req.getCustomerPhone() == null)) {
        // throw new CustomException("Address and phone required for delivery");
        // }

        // double total = 0;

        // for (OrderItemDto item : req.getItems()) {
        // ShopProduct sp = spRepo.findById(item.getShopProductId())
        // .orElseThrow(() -> new CustomException("Product not found"));

        // if (sp.getQuantity() < item.getQuantity()) {
        // throw new CustomException("Insufficient stock for " +
        // sp.getProduct().getName());
        // }

        // total += sp.getPrice() * item.getQuantity();
        // }

        // if ("DELIVERY".equals(req.getDeliveryType())) {
        // total += shop.getDeliveryCharge();
        // }

        // Order order = new Order();
        // order.setCustomerName(req.getCustomerName());
        // order.setCustomerPhone(req.getCustomerPhone());
        // order.setCustomerAddress(req.getCustomerAddress());
        // order.setDeliveryType(DeliveryType.valueOf(req.getDeliveryType())+"");
        // // order.setStatus(OrderStatus.PLACED);
        // order.setTotalAmount(total);
        // order.setShop(shop);

        // orderRepo.save(order);

        // return order;
        return null;
    }
}