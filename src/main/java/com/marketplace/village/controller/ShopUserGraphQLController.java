package com.marketplace.village.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.marketplace.village.entity.ShopUser;
import com.marketplace.village.repository.ShopUserRepository;

@Controller
@CrossOrigin(origins = "*")
public class ShopUserGraphQLController {

    @Autowired
    private ShopUserRepository repository;

    @QueryMapping
    public ShopUser shopUser(@Argument UUID id) {
        return repository.findById(id).orElse(null);
    }
}