package com.marketplace.village.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketplace.village.entity.ShopUser;

@Repository
public interface ShopUserRepository extends JpaRepository<ShopUser, UUID> {

    ShopUser findByContact(String contact);

    List<ShopUser> findByIsDeleteFalseAndIsActiveTrue();

    ShopUser findByContactAndPassword(String contact, String password);

    

    @Query("Select s from ShopUser s where s.contact = :contact and s.id != :id")
    ShopUser findByIdAndContact(UUID id, String contact);

    List<ShopUser> findByPinCodeOrderByUpdatedAtAsc(String pinCode);

    ShopUser findByEmail(String email);
    
}
