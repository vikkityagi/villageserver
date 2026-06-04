package com.marketplace.village.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketplace.village.entity.Customer;
import java.util.UUID;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    

    Customer findByShop_Id(UUID shopId);

    Customer findByCustomerPhone(String phone);

    @Query("SELECT c FROM Customer c WHERE c.customerPhone = :customerPhone AND c.shop.id = :shopId")
    Customer findByCustomerPhoneAndShopId(String customerPhone, UUID shopId);

    List<Customer> findByIsDeletedFalseAndIsActiveTrue();

    
}