package com.marketplace.village.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.village.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}