package com.marketplace.village.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.marketplace.village.dto.ShopDto;
import com.marketplace.village.dto.ShopProductDto;
import com.marketplace.village.dto.ShopProductRequestDto;
import com.marketplace.village.dto.ShopUserDto;
import com.marketplace.village.entity.Category;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopProduct;
import com.marketplace.village.entity.ShopUser;
import com.marketplace.village.exception.CustomException;
import com.marketplace.village.repository.CategoryRepository;
import com.marketplace.village.repository.ShopProductRepository;
import com.marketplace.village.repository.ShopRepository;
import com.marketplace.village.repository.ShopUserRepository;
import com.marketplace.village.service.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepo;

    @Autowired
    private ShopUserRepository shopUserRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShopProductRepository shopProductRepository;

    // manage shopuser

    @Override
    public List<ShopUserDto> getActiveShopUser() {
        List<ShopUser> shopUsers = shopUserRepository.findByIsDeleteFalseAndIsActiveTrue();
        List<ShopUserDto> shopUserDtos = new ArrayList<>();
        for (ShopUser shopUser : shopUsers) {
            ShopUserDto shopUserDto = new ShopUserDto();

            shopUserDto = convertShopUserToShopUserDto(shopUser);

            shopUserDtos.add(shopUserDto);
        }

        return shopUserDtos;

    }

    @Override
    public ShopUserDto addShopUser(ShopUserDto shopUserDto) throws Exception {

        if (!shopUserDto.getPassword().equals(shopUserDto.getConfirmPassword())) {
            throw new CustomException("Password and Confirm Password should be same");
        }

        if (shopUserDto.getPassword().length() < 6) {
            throw new CustomException("Password should be at least 6 characters");
        }
        // TODO Auto-generated method stub
        ShopUser dbShopUser = shopUserRepository.findByContact(shopUserDto.getContact());

        if (dbShopUser != null) {
            throw new CustomException("ShopUser already Register");
        }

        if (shopUserDto.getEmail() != null) {
            ShopUser shopUserByEmail = shopUserRepository.findByEmail(shopUserDto.getEmail());
            if (shopUserByEmail != null) {
                throw new CustomException("ShopUser already Register with this email");
            }
        }

        ShopUser shopUser = new ShopUser();

        shopUser = convertShopUserDtoToShopUser(shopUserDto);

        ShopUser savedShopUser = this.shopUserRepository.save(shopUser);

        return convertShopUserToShopUserDto(savedShopUser);

    }

    @Override
    public ShopUserDto loginShopUser(ShopUserDto shopUserDto) throws Exception {
        // TODO Auto-generated method stub
        ShopUser shopUser = this.shopUserRepository.findByContact(shopUserDto.getContact());

        if (shopUser == null) {
            throw new CustomException("ShopUser is not register with this contact");
        }

        if (shopUser.getIsDelete()) {
            throw new CustomException("ShopUser is deleted");
        }

        // if (!shopUser.getIsActive()) {
        // throw new CustomException("ShopUser is not active");
        // }

        if (shopUser.getPassword() == null) {
            throw new CustomException("Password should not be null");
        }

        if (shopUserDto.getPassword() == null) {
            throw new CustomException("Password should not be null");
        }

        if (!shopUser.getContact().equals(shopUserDto.getContact())) {
            throw new CustomException("Invalid contact");
        }

        if (!shopUser.getPassword().equals(shopUserDto.getPassword())) {
            throw new CustomException("Invalid password");
        }

        shopUserDto = convertShopUserToShopUserDto(shopUser);

        return shopUserDto;
    }

    @Override
    public ShopUserDto getShopUserById(UUID id) throws Exception {
        // TODO Auto-generated method stub
        Optional<ShopUser> shopUser = this.shopUserRepository.findById(id);

        if (!shopUser.isPresent()) {
            throw new CustomException("User not found");
        }

        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserDto = convertShopUserToShopUserDto(shopUser.get());

        return shopUserDto;

    }

    public ShopUserDto forgotInfo(ShopUserDto shopUserDto) throws Exception {
        // TODO Auto-generated method stub
        ShopUser shopUser = this.shopUserRepository.findByContact(shopUserDto.getContact());

        if (shopUser == null) {
            throw new CustomException("Mobile Number does not match");
        }

        shopUser = convertShopUserDtoToShopUser(shopUserDto);

        shopUserDto = convertShopUserToShopUserDto(shopUser);

        return shopUserDto;
    }

    @Override
    public ShopUserDto updateShopUser(UUID id, ShopUserDto shopUserDto) throws Exception {

        if (!shopUserDto.getPassword().equals(shopUserDto.getConfirmPassword())) {
            throw new CustomException("Password and Confirm Password should be same");
        }

        if (shopUserDto.getPassword().length() < 6) {
            throw new CustomException("Password should be at least 6 characters");
        }

        // TODO Auto-generated method stub
        Optional<ShopUser> dbShopUser = this.shopUserRepository.findById(id);

        if (!dbShopUser.isPresent()) {
            throw new CustomException("User not found");
        }

        if (!shopUserDto.getEmail().equalsIgnoreCase(dbShopUser.get().getEmail())) {
            ShopUser shopUserByEmail = shopUserRepository.findByEmail(shopUserDto.getEmail());
            if (!shopUserByEmail.getEmail().equalsIgnoreCase(dbShopUser.get().getEmail())) {

            }
        }

        ShopUser shopUser = dbShopUser.get();
        shopUser.setName(shopUserDto.getName());
        shopUser.setShopAddress(shopUserDto.getShopAddress());
        shopUser.setContact(shopUserDto.getContact());
        shopUser.setEmail(shopUserDto.getEmail());
        shopUser.setPassword(shopUserDto.getPassword());
        shopUser.setConfirmPassword(shopUserDto.getConfirmPassword());
        shopUser.setAltContact(shopUserDto.getAltContact());
        shopUser.setIsActive(shopUserDto.getIsActive());
        shopUser.setPinCode(shopUserDto.getPinCode());
        shopUser.setDeliveryAvailable(shopUserDto.getDeliveryAvailable());

        if (shopUserDto.getProfileImageUrl() != null) {
            // shopUserDto.setProfileImageUrl("abc,"+shopUser.getProfileImageUrl());
            byte[] imageBytes = Base64.getDecoder()
                    .decode(shopUserDto.getProfileImageUrl().split(",")[1]);

            shopUser.setProfileImageUrl(imageBytes);
        }

        shopUser.setCreatedAt(shopUser.getCreatedAt());
        shopUser.setUpdatedAt(LocalDateTime.now());

        shopUser = this.shopUserRepository.save(shopUser);

        shopUserDto = convertShopUserToShopUserDto(shopUser);

        return shopUserDto;
    }

    @Override
    public void deleteShopUser(UUID id) throws Exception {
        // TODO Auto-generated method stub
        Optional<ShopUser> shopUser = this.shopUserRepository.findById(id);

        if (!shopUser.isPresent()) {
            throw new CustomException("User not found");
        }

        shopUser.get().setIsDelete(true);

        shopUserRepository.save(shopUser.get());

        // ShopUserDto shopUserDto = new ShopUserDto();
        // shopUserDto = convertShopUserToShopUserDto(shopUser.get());

    }

    @Override
    public List<ShopUserDto> findShopUserByContact(String mobileno) throws Exception {
        ShopUser shopUser = shopUserRepository.findByContact(mobileno);
        if (shopUser.getContact() == null) {
            throw new CustomException("Mobile no should not be null");
        }

        List<ShopUserDto> shopUserDtos = new ArrayList<>();

        shopUserDtos.add(convertShopUserToShopUserDto(shopUser));

        return shopUserDtos;

    }

    @Override
    public List<ShopUserDto> findShopUserByPinCodeForTool(String pinCode) {
        List<ShopUser> shopUserList = shopUserRepository.findByPinCodeOrderByUpdatedAtAsc(pinCode);
        if (shopUserList.size() <= 0) {
            throw new CustomException("Pin code should not match with any shop user");
        }

        List<ShopUserDto> shopUserDtos = new ArrayList<>();

        for (ShopUser shopUser : shopUserList) {
            ShopUserDto shopUserDto = new ShopUserDto();
            shopUserDto.setName(shopUser.getName());
            shopUserDto.setShopAddress(shopUser.getShopAddress());
            shopUserDto.setContact(shopUser.getContact());
            shopUserDto.setAltContact(shopUser.getAltContact());
            shopUserDto.setPinCode(pinCode);
            shopUserDto.setLastTimeActivity(shopUser.getUpdatedAt());

            shopUserDtos.add(shopUserDto);
        }

        return shopUserDtos;

    }

    @Override
    public List<ShopUserDto> findShopUserByPinCode(String pinCode) {
        List<ShopUser> shopUserList = shopUserRepository.findByPinCodeOrderByUpdatedAtAsc(pinCode);
        if (shopUserList.size() <= 0) {
            throw new CustomException("Pin code should not match with any shop user");
        }

        List<ShopUserDto> shopUserDtos = new ArrayList<>();

        for (ShopUser shopUser : shopUserList) {
            ShopUserDto shopUserDto = new ShopUserDto();
            shopUserDto = convertShopUserToShopUserDto(shopUser);
            shopUserDto.setLastTimeActivity(shopUser.getUpdatedAt());

            shopUserDtos.add(shopUserDto);
        }

        return shopUserDtos;

    }

    private ShopUserDto convertShopUserToShopUserDto(ShopUser shopUser) {
        ShopUserDto shopUserDto = new ShopUserDto();
        shopUserDto.setId(shopUser.getId());
        shopUserDto.setName(shopUser.getName());
        shopUserDto.setShopAddress(shopUser.getShopAddress());
        shopUserDto.setContact(shopUser.getContact());
        shopUserDto.setPassword("XXX");
        shopUserDto.setConfirmPassword("XXX");
        shopUserDto.setAltContact(shopUser.getAltContact());
        shopUserDto.setIsActive(shopUser.getIsActive());
        shopUserDto.setEmail(shopUser.getEmail());
        shopUserDto.setIsDelete(shopUser.getIsDelete());
        shopUserDto.setPinCode(shopUser.getPinCode());
        shopUserDto.setDeliveryAvailable(shopUser.getDeliveryAvailable());
        shopUserDto.setProfileImageUrl(getShopUserImage(shopUser.getId()));
        // shopUserDto.setCategory(shopUser.getCategory());
        if (shopUser.getCategory().size() != 0) {
            for (Category category : shopUser.getCategory()) {
                if (category.getShops().size() != 0) {
                    List<ShopDto> shops = category.getShops()
                            .stream()
                            .map(shop -> {
                                ShopDto dto = new ShopDto();
                                dto.setId(shop.getId());
                                dto.setShopName(shop.getShopName());
                                return dto;
                            })
                            .toList();

                    shopUserDto.setShops(shops);
                }
            }
        }
        return shopUserDto;
    }

    private ShopUser convertShopUserDtoToShopUser(ShopUserDto shopUserDto) {
        ShopUser shopUser = new ShopUser();
        shopUser.setName(shopUserDto.getName());
        shopUser.setShopAddress(shopUserDto.getShopAddress());
        shopUser.setContact(shopUserDto.getContact());
        shopUser.setPassword(shopUserDto.getPassword());
        shopUser.setConfirmPassword(shopUserDto.getConfirmPassword());
        shopUser.setAltContact(shopUserDto.getAltContact());
        shopUser.setIsActive(shopUserDto.getIsActive());
        shopUser.setPinCode(shopUserDto.getPinCode());
        shopUser.setEmail(shopUserDto.getEmail());
        shopUser.setDeliveryAvailable(shopUserDto.getDeliveryAvailable());
        byte[] imageBytes = Base64.getDecoder()
                .decode(shopUserDto.getProfileImageUrl().split(",")[1]);

        shopUser.setProfileImageUrl(imageBytes);

        shopUser.setCreatedAt(LocalDateTime.now());
        shopUser.setUpdatedAt(LocalDateTime.now());
        // shopUser.setIsDeleted(shopUserDto.getIsDelete());
        return shopUser;
    }

    // manage shop

    @Override
    public ShopDto registerShop(ShopDto shopDto) throws Exception {
        // TODO Auto-generated method stub
        Shop shop = new Shop();

        shop = convertShopDtoToEntity(shopDto);

        shop = this.shopRepo.save(shop);

        shopDto = convertShopEntityToDto(shop);

        return shopDto;
    }

    @Override
    public ShopDto updateShop(UUID id, ShopDto shopDto) throws Exception {
        // TODO Auto-generated method stub
        Optional<Shop> shopOptional = this.shopRepo.findById(id);
        if (!shopOptional.isPresent()) {
            throw new CustomException("Shop not found");
        }

        Shop shop = shopOptional.get();

        shop = convertShopDtoToEntity(shopDto);

        shop = this.shopRepo.save(shop);

        shopDto = convertShopEntityToDto(shop);

        return shopDto;

    }

    @Override
    public List<ShopDto> getActiveShop() throws Exception {
        // TODO Auto-generated method stub
        List<Shop> shopList = shopRepo.findByIsDeletedFalseAndIsActiveTrue();
        List<ShopDto> shopDtos = new ArrayList<>();

        for (Shop shop : shopList) {
            ShopDto shopDto = new ShopDto();

            shopDto = convertShopEntityToDto(shop);
            shopDtos.add(shopDto);

        }

        return shopDtos;
    }

    @Override
    public List<ShopDto> activeShopUsingCategoryId(UUID categoryId) {
        // TODO Auto-generated method stub
        List<Shop> shopList = shopRepo.findByIsDeletedFalseAndIsActiveTrue(categoryId);
        List<ShopDto> shopDtos = new ArrayList<>();

        for (Shop shop : shopList) {
            ShopDto shopDto = new ShopDto();

            shopDto = convertShopEntityToDto(shop);
            shopDtos.add(shopDto);

        }

        return shopDtos;
    }

    @Override
    public List<ShopDto> getAllShops() throws Exception {
        // TODO Auto-generated method stub
        List<Shop> shopList = shopRepo.findByIsDeletedFalse();
        List<ShopDto> shopDtos = new ArrayList<>();

        for (Shop shop : shopList) {
            ShopDto shopDto = new ShopDto();

            shopDto = convertShopEntityToDto(shop);
            shopDtos.add(shopDto);

        }

        return shopDtos;
    }

    @Override
    public List<ShopDto> getAllShopsByShopUserIdAndCategoryId(UUID shopId, UUID categoryId) throws Exception {
        // TODO Auto-generated method stub
        List<Shop> shopList = shopRepo.findByIsDeletedFalseAndShopUserIdAndCategoryId(shopId, categoryId);
        List<ShopDto> shopDtos = new ArrayList<>();

        for (Shop shop : shopList) {
            ShopDto shopDto = new ShopDto();

            shopDto = convertShopEntityToDto(shop);
            shopDtos.add(shopDto);

        }

        return shopDtos;
    }

    @Override
    public ShopDto getShopById(UUID id) throws Exception {
        // TODO Auto-generated method stub
        Optional<Shop> shopOptional = this.shopRepo.findById(id);
        if (!shopOptional.isPresent()) {
            throw new CustomException("Shop not found");
        }

        Shop shop = shopOptional.get();

        ShopDto shopDto = convertShopEntityToDto(shop);

        return shopDto;
    }

    @Override
    public void deleteShop(UUID id) throws Exception {
        // TODO Auto-generated method stub
        Optional<Shop> shopOptional = this.shopRepo.findById(id);
        if (!shopOptional.isPresent()) {
            throw new CustomException("Shop not found");
        }

        Shop shop = shopOptional.get();

        shop.setIsDeleted(true);

        shop = shopRepo.save(shop);

    }

    private ShopDto convertShopEntityToDto(Shop shop) {

        ShopDto shopDto = new ShopDto();

        shopDto.setId(shop.getId());

        shopDto.setShopName(shop.getShopName());
        shopDto.setShopAddress(shop.getShopAddress());

        Optional<Category> categoryOptional = this.categoryRepository.findById(shop.getCategory().getId());
        if (!categoryOptional.isPresent()) {
            throw new CustomException("Category not found");
        }

        shopDto.setCategory(categoryOptional.get().getId());
        shopDto.setDeliveryAvailable(shop.getDeliveryAvailable());
        shopDto.setDeliveryCharge(shop.getDeliveryCharge());

        shopDto.setContact(categoryOptional.get().getShopUser().getContact());
        shopDto.setPinCode(shop.getPinCode());
        shopDto.setShopAddress(shop.getShopAddress());
        shopDto.setOpenTime(shop.getOpenTime());
        shopDto.setCloseTime(shop.getCloseTime());

        Optional<ShopUser> shopUserOptional = this.shopUserRepository.findById(shop.getShopUser().getId());

        if (!shopUserOptional.isPresent()) {
            throw new CustomException("ShopUser not found");
        }
        shopDto.setShopUser(shopUserOptional.get().getId());

        shopDto.setIsActive(shop.getIsActive());
        shopDto.setIsDeleted(shop.getIsDeleted());
        shopDto.setShopImage(getImage(shop.getId()));

        return shopDto;

    }

    public String getImage(UUID id) {

        String base64Image = "";

        Shop shop = shopRepo.findById(id).get();

        byte[] imageBytes = shop.getShopImage();

        if (imageBytes != null && imageBytes.length > 0) {

            base64Image = Base64.getEncoder().encodeToString(imageBytes);

        } else {

            base64Image = ""; // or return a default image URL
        }

        return base64Image;
    }

    private String getShopUserImage(UUID id) {

        String base64Image = "";
        ShopUser shopUser = shopUserRepository.findById(id).get();

        byte[] imageBytes = shopUser.getProfileImageUrl();

        if (imageBytes != null && imageBytes.length > 0) {
            base64Image = Base64.getEncoder().encodeToString(imageBytes);

        } else {
            base64Image = ""; // or return a default image URL
        }

        return base64Image;
    }

    private Shop convertShopDtoToEntity(ShopDto shopDto) throws Exception {

        Shop shop = new Shop();
        shop.setShopName(shopDto.getShopName());
        shop.setShopAddress(shopDto.getShopAddress());

        Optional<Category> categoryOptional = this.categoryRepository.findById(shopDto.getCategory());
        if (!categoryOptional.isPresent()) {
            throw new CustomException("Category not found");
        }

        shop.setCategory(categoryOptional.get());

        shop.setContact(categoryOptional.get().getShopUser().getContact());
        shop.setAltContact(categoryOptional.get().getShopUser().getAltContact());
        shop.setIsActive(shopDto.getIsActive());
        shop.setDeliveryAvailable(shopDto.getDeliveryAvailable());
        if (shopDto.getDeliveryAvailable()) {
            shop.setDeliveryCharge(shopDto.getDeliveryCharge());
        } else {
            shop.setDeliveryCharge(0.0);
        }

        shop.setPinCode(shopDto.getPinCode());

        byte[] imageBytes = Base64.getDecoder()
                .decode(shopDto.getShopImage().split(",")[1]);

        shop.setShopImage(imageBytes);

        shop.setRole(shopDto.getRole());
        shop.setShopDescription(shopDto.getShopDescription());
        shop.setOpenTime(shopDto.getOpenTime());
        shop.setCloseTime(shopDto.getCloseTime());
        shop.setShopLicenseNumber(shopDto.getShopLicenseNumber());
        shop.setShopRegistrationNumber(shopDto.getShopRegistrationNumber());
        shop.setShopGSTNumber(shopDto.getShopGSTNumber());
        shop.setShopPanNumber(shopDto.getShopPanNumber());
        shop.setShopAadharNumber(shopDto.getShopAadharNumber());
        shop.setShopFSSAINumber(shopDto.getShopFSSAINumber());
        shop.setShopBankAccountNumber(shopDto.getShopBankAccountNumber());
        shop.setShopIFSCCode(shopDto.getShopIFSCCode());
        shop.setShopOwnerName(shopDto.getShopOwnerName());

        shop.setCreatedAt(LocalDateTime.now());
        shop.setUpdatedAt(LocalDateTime.now());

        Optional<ShopUser> shopUserOptional = this.shopUserRepository.findById(shopDto.getShopUser());

        if (!shopUserOptional.isPresent()) {
            throw new CustomException("ShopUser not found");
        }
        shop.setShopUser(shopUserOptional.get());

        return shop;

    }

    // manage shopproudct

    @Override
    public ShopProductRequestDto addShopProducts(ShopProductRequestDto shopProductRequestDto) throws Exception {

        // 1. Fetch shop
        Optional<Shop> shop = shopRepo.findById(shopProductRequestDto.getShopId());

        if (!shop.isPresent()) {
            throw new CustomException("Shop not present");
        }

        // 2. Convert DTO → Entity

        List<ShopProductDto> productsDto = shopProductRequestDto.getProducts();
        List<ShopProduct> products = new ArrayList<>();

        for (ShopProductDto dto : productsDto) {

            ShopProduct product = new ShopProduct();

            product.setShop(shop.get());
            product.setProductName(dto.getProductName());
            product.setProductPrice(dto.getProductPrice());
            product.setProductQuantity(dto.getProductQuantity());
            product.setProductUnit(dto.getProductUnit());
            product.setProductDescription(dto.getProductDescription());
            // product.setProductImageUrl(dto.getProductImageUrl());
            product.setProductPinCode(dto.getProductPinCode());
            product.setIsActive(dto.getIsActive());
            product.setCreatedAt(LocalDateTime.now());
            product.setIsDelete(false);

            products.add(product);

        }

        List<ShopProduct> savedProducts = shopProductRepository.saveAll(products);

        shopProductRequestDto = convertShopProducEntityToShopProductRequestDto(shopProductRequestDto.getShopId(),
                savedProducts);

        return shopProductRequestDto;

    }

    @Override
    public ShopProductDto editShopProduct(UUID id, ShopProductDto shopProductDto) throws Exception {

        // 1. Fetch existing product
        ShopProduct product = shopProductRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found"));

        // 2. Update fields (only if not null - SAFE UPDATE)
        if (shopProductDto.getProductName() != null) {
            product.setProductName(shopProductDto.getProductName());
        }

        if (shopProductDto.getProductPrice() != null) {
            product.setProductPrice(shopProductDto.getProductPrice());
        }

        if (shopProductDto.getProductQuantity() != null) {
            product.setProductQuantity(shopProductDto.getProductQuantity());
        }

        if (shopProductDto.getProductUnit() != null) {
            product.setProductUnit(shopProductDto.getProductUnit());
        }

        if (shopProductDto.getProductDescription() != null) {
            product.setProductDescription(shopProductDto.getProductDescription());
        }

        // if (shopProductDto.getProductImageUrl() != null) {
        // product.setProductImageUrl(shopProductDto.getProductImageUrl());
        // }

        if (shopProductDto.getProductPinCode() != null) {
            product.setProductPinCode(shopProductDto.getProductPinCode());
        }

        if (shopProductDto.getIsActive() != null) {
            product.setIsActive(shopProductDto.getIsActive());
        }

        // Optional: update shop if needed
        if (shopProductDto.getShopId() != null) {
            Shop shop = shopRepo.findById(shopProductDto.getShopId())
                    .orElseThrow(() -> new CustomException("Shop not found"));
            product.setShop(shop);
        }

        // 3. Update timestamp
        product.setUpdatedAt(LocalDateTime.now());

        // 4. Save updated product
        ShopProduct updatedProduct = shopProductRepository.save(product);

        // 5. Convert Entity → DTO

        shopProductDto = convertShopProductToDto(updatedProduct);

        return shopProductDto;
    }

    @Override
    public ShopProductDto getShopProductById(UUID id) throws Exception {
        // TODO Auto-generated method stub
        Optional<ShopProduct> shopProducOptional = shopProductRepository.findById(id);

        if (!shopProducOptional.isPresent()) {
            throw new CustomException("ShopProduct not found");
        }

        ShopProduct shopProduct = shopProducOptional.get();

        ShopProductDto shopProductDto = convertShopProductToDto(shopProduct);

        return shopProductDto;

    }

    @Override
    public List<ShopProductDto> getAllActiveShopProduct() throws Exception {
        // TODO Auto-generated method stub
        List<ShopProduct> shopProducts = shopProductRepository.findByIsDeleteFalseAndIsActiveTrue();
        List<ShopProductDto> shopProductDtos = new ArrayList<>();

        if (shopProducts == null) {
            throw new CustomException("ShopProducts not getting");
        }

        for (ShopProduct shopProduct : shopProducts) {
            ShopProductDto shopProductDto = convertShopProductToDto(shopProduct);

            shopProductDtos.add(shopProductDto);
        }

        return shopProductDtos;
    }

    @Override
    public void deleteShopProduct(UUID id) throws Exception {
        // TODO Auto-generated method stub
        Optional<ShopProduct> shopProducOptional = shopProductRepository.findById(id);

        if (!shopProducOptional.isPresent()) {
            throw new CustomException("ShopProduct not found");
        }

        ShopProduct shopProduct = shopProducOptional.get();

        shopProduct.setIsDelete(true);

        ShopProductDto shopProductDto = convertShopProductToDto(shopProduct);

    }

    private ShopProductRequestDto convertShopProducEntityToShopProductRequestDto(UUID shopId,
            List<ShopProduct> savedProducts) {

        ShopProductRequestDto shopProductRequestDto = new ShopProductRequestDto();

        List<ShopProductDto> responseDtos = new ArrayList<>();

        for (ShopProduct product : savedProducts) {

            ShopProductDto dto = new ShopProductDto();

            dto.setId(product.getId());
            dto.setShopId(product.getShop().getId()); // IMPORTANT
            dto.setProductName(product.getProductName());
            dto.setProductPrice(product.getProductPrice());
            dto.setProductQuantity(product.getProductQuantity());
            dto.setProductUnit(product.getProductUnit());
            dto.setProductDescription(product.getProductDescription());
            // dto.setProductImageUrl(product.getProductImageUrl());
            dto.setProductPinCode(product.getProductPinCode());
            dto.setIsActive(product.getIsActive());

            responseDtos.add(dto);
        }

        shopProductRequestDto.setShopId(shopId);
        shopProductRequestDto.setProducts(responseDtos);

        return shopProductRequestDto;

    }

    private ShopProductDto convertShopProductToDto(ShopProduct saveProduct) {

        ShopProductDto response = new ShopProductDto();

        response.setId(saveProduct.getId());
        response.setShopId(saveProduct.getShop().getId());
        response.setProductName(saveProduct.getProductName());
        response.setProductPrice(saveProduct.getProductPrice());
        response.setProductQuantity(saveProduct.getProductQuantity());
        response.setProductUnit(saveProduct.getProductUnit());
        response.setProductDescription(saveProduct.getProductDescription());
        response.setProductImageUrl(saveProduct.getProductImageUrl());
        response.setProductPinCode(saveProduct.getProductPinCode());
        response.setIsActive(saveProduct.getIsActive());

        return response;

    }
}
