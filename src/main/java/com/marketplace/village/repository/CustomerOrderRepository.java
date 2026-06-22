package com.marketplace.village.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketplace.village.entity.CustomerOrder;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,UUID> {

    
    

    @Query("Select c from CustomerOrder c where c.isActive=true and c.phoneNumber=:phoneNumber")
    List<CustomerOrder> findAllActiveOrderByPhoneNumber(String phoneNumber);

    @Query("Select c from CustomerOrder c where c.delete = false and c.shopId=:id")
    List<CustomerOrder> findOrderByShopId(UUID id);

    @Query("Select c from CustomerOrder c where c.phoneNumber=:phoneNumber")
    List<CustomerOrder> findAllOrderByPhoneNumber(String phoneNumber);
    
}
