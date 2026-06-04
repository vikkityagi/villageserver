package com.marketplace.village.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.village.dto.CustomerDto;
import com.marketplace.village.entity.Customer;
// import com.marketplace.village.entity.Product;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopProduct;
import com.marketplace.village.repository.CustomerRepository;
import com.marketplace.village.repository.ShopProductRepository;
// import com.marketplace.village.repository.ProductRepository;
import com.marketplace.village.repository.ShopRepository;
import com.marketplace.village.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopProductRepository shopProductRepository;

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) {
        // TODO Auto-generated method stub
        List<ShopProduct> products = new ArrayList<>();

        Optional<Shop> shopOpt = shopRepository.findById(customerDto.getShopId());

        if (!shopOpt.isPresent()) {
            throw new RuntimeException("Shop not found with id: " + customerDto.getShopId());
        }

        for (UUID product : customerDto.getProductIds()) {
            Optional<ShopProduct> productOpt = shopProductRepository.findById(product);
            if (!productOpt.isPresent()) {
                throw new RuntimeException("Product not found with id: " + product);
            }
            products.add(productOpt.get());
        }

        // Optional<Product> productOpt =
        // productRepository.findById(customerDto.getProductIds());

        UUID shopId = shopOpt.get().getId();

        Customer newCustomer = new Customer();
        if (shopId.equals(customerDto.getShopId())) {
            Customer dbUser = customerRepository.findByCustomerPhoneAndShopId(customerDto.getCustomerPhone(), shopId);
            if (dbUser.getCustomerPhone().equals(customerDto.getCustomerPhone())
                    && dbUser.getShop().getId().equals(shopId)) {
                throw new RuntimeException("Customer already registered");
            } else if (!dbUser.getShop().getId().equals(shopId)) {
                newCustomer.setCustomerName(customerDto.getCustomerName());
                newCustomer.setCustomerAddress(customerDto.getCustomerAddress());
                newCustomer.setCustomerPhone(customerDto.getCustomerPhone());

                newCustomer.setPassword(customerDto.getPassword());

                newCustomer.setIsActive(true);

                newCustomer.setCreatedAt(LocalDateTime.now());
                newCustomer.setProduct(products);

                newCustomer.setCustomerPinCode(customerDto.getCustomerPinCode());
                newCustomer.setShop(shopOpt.get());

                customerRepository.save(newCustomer);
            }
        } else {

            throw new RuntimeException("Shop not found with id: " + customerDto.getShopId());

        }

        return customerDto;

    }

    @Override
    public CustomerDto loginCustomer(String phone, String password) {
        // TODO Auto-generated method stub
        Customer dbUser = customerRepository.findByCustomerPhone(phone);
        if (dbUser == null) {
            throw new RuntimeException("Customer not found with phone: " + phone);
        } else if (!dbUser.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return convertToDto(dbUser);

    }

    @Override
    public CustomerDto forgotPassword(String phone) {
        // TODO Auto-generated method stub
        Customer dbUser = customerRepository.findByCustomerPhone(phone);
        if (dbUser == null) {
            throw new RuntimeException("Customer not found with phone: " + phone);
        }
        dbUser.setPassword("newpassword");
        customerRepository.save(dbUser);
        return convertToDto(dbUser);
    }

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setCustomerPhone(customer.getCustomerPhone());
        dto.setCustomerAddress(customer.getCustomerAddress());
        dto.setCustomerPinCode(customer.getCustomerPinCode());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        dto.setShopId(customer.getShop().getId());

        List<UUID> productIds = new ArrayList<>();
        for (ShopProduct product : customer.getProduct()) {
            productIds.add(product.getId());
        }
        dto.setProductIds(productIds);

        dto.setIsActive(customer.getIsActive());
        dto.setIsDeleted(customer.getIsDeleted());

        return dto;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        // TODO Auto-generated method stub
        List<Customer> customers = customerRepository.findByIsDeletedFalseAndIsActiveTrue();

        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(convertToDto(customer));
        }
        return customerDtos;
    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        // TODO Auto-generated method stub
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        return convertToDto(customerOpt.get());
    }

    @Override
    public CustomerDto updateCustomer(UUID id, CustomerDto customerDto) {
        // TODO Auto-generated method stub
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        Customer customer = customerOpt.get();
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setCustomerAddress(customerDto.getCustomerAddress());
        customer.setCustomerPhone(customerDto.getCustomerPhone());
        customer.setCustomerPinCode(customerDto.getCustomerPinCode());  
        customer.setUpdatedAt(LocalDateTime.now());
        customerRepository.save(customer);
        return convertToDto(customer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        Optional<Customer> customerOpt = customerRepository.findById(id);
        if (!customerOpt.isPresent()) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        Customer customer = customerOpt.get();
        customer.setIsDeleted(true);
        customerRepository.save(customer);
    }

}
