package com.marketplace.village.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.village.dto.ShopDto;
import com.marketplace.village.dto.ShopProductDto;
import com.marketplace.village.dto.ShopProductRequestDto;
import com.marketplace.village.dto.ShopUserDto;
import com.marketplace.village.entity.Shop;
import com.marketplace.village.entity.ShopUser;
import com.marketplace.village.exception.CustomException;
import com.marketplace.village.repository.ShopRepository;
import com.marketplace.village.service.ShopService;

@RestController
@CrossOrigin(origins = "*")
public class ShopController {

    @Autowired
    private ShopService shopService;

    // manage shopuser

    @PostMapping("/shop_users")
    public ResponseEntity<ShopUserDto> addShopUser(@RequestBody ShopUserDto shopUserDto) throws Exception {
        try{
            shopUserDto = this.shopService.addShopUser(shopUserDto);
            return new ResponseEntity<>(shopUserDto,HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }catch(Throwable e){
            System.out.println("Throwable caught: " + e.getMessage());
            throw e;
        }
        
    }

    @PostMapping("/shop_user/login")
    public ResponseEntity<ShopUserDto> loginShopUser(@RequestBody ShopUserDto shopUserDto)throws Exception {
        
        try{
            shopUserDto = this.shopService.loginShopUser(shopUserDto);
            return new ResponseEntity<>(shopUserDto,HttpStatus.OK);
        }
        catch(Exception e){
            throw e;
        }
        catch(Throwable e){
            System.out.println("Throwable caught: " + e.getMessage());
            throw e;
        }
            
    }

    @PatchMapping("/shop_user/forgot")
    public ResponseEntity<ShopUserDto> forgotInfo(@RequestBody ShopUserDto shopUserDto)throws Exception {
        
        try{
            shopUserDto = this.shopService.forgotInfo(shopUserDto);
            return new ResponseEntity<>(shopUserDto,HttpStatus.OK);
        }
        catch(Exception e){
            throw e;
        }
        catch(Throwable e){
            System.out.println("Throwable caught: " + e.getMessage());
            throw e;
        }
            
    }

    

    

    @GetMapping("/active/shop_users")
    public ResponseEntity<List<ShopUserDto>> getActiveShopUser() throws Exception {
        List<ShopUserDto> shopUserDtos = new ArrayList<>();
        try{
            shopUserDtos = shopService.getActiveShopUser();
            return new ResponseEntity<>(shopUserDtos,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }catch(Throwable e){
            System.out.println("Throwable caught: " + e.getMessage());
            throw e;
        }
        
    }

    @PutMapping("/shop_users/{id}")
    public ResponseEntity<ShopUserDto> updateShopUser(@PathVariable UUID id,@RequestBody ShopUserDto shopUserDto) throws Exception {
        try{
            shopUserDto = shopService.updateShopUser(id, shopUserDto);
            return new ResponseEntity<>(shopUserDto,HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
        catch(Throwable t){
            System.out.println("Throwable caught: " + t.getMessage());
            throw t;
        }
        

    }

    @GetMapping("/shop_user/{id}")
    public ResponseEntity<ShopUserDto> getShopUserById(@PathVariable UUID id) throws Exception {
        ShopUserDto shopUserDto = new ShopUserDto();
        try{
            shopUserDto = shopService.getShopUserById(id);
            return new ResponseEntity<>(shopUserDto,HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
        catch(Throwable e){
            System.out.println("Throwable caught: " + e.getMessage());
            throw e;
        }
        
    }

    @GetMapping("/shop_users/{pincode}")
    public ResponseEntity<List<ShopUserDto>> findShopUserByPinCode(@PathVariable String pincode) throws Exception {
        List<ShopUserDto> shopUserDtoList = new ArrayList<>();
        
            shopUserDtoList = shopService.findShopUserByPinCode(pincode);
            return new ResponseEntity<>(shopUserDtoList,HttpStatus.OK);
        
        
    }

    @DeleteMapping("/shop_user/{id}")
    public void deleteShopUser(@PathVariable UUID id){
        try{
            shopService.deleteShopUser(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    
    @GetMapping("/shop_user/find_by_contact/{contact}")
    public ResponseEntity<List<ShopUserDto>> findShopUserByContact(@PathVariable String contact) throws Exception {
        List<ShopUserDto> shopUserDtos = new ArrayList<>();
        try{
            shopUserDtos = shopService.findShopUserByContact(contact);
            return new ResponseEntity<>(shopUserDtos,HttpStatus.OK);
        }catch(Exception e){
            throw e;
        }
        catch(Throwable e){
            System.out.println("Throwable caught: " + e.getMessage());
            throw e;
        }
        
    }

    // manage shop

    @PostMapping("/shops")
    public ResponseEntity<ShopDto> addShop(@RequestBody ShopDto shopDto) throws Exception {
        try{
            shopDto = shopService.registerShop(shopDto);
            return new ResponseEntity<>(shopDto,HttpStatus.CREATED);
        }catch(Exception e){
            throw e;
        }catch(Throwable t){
            System.out.println("Throwable caught: " + t.getMessage());
            throw t;
        }
        
    }

    @PutMapping("/shop/{id}")
    public ResponseEntity<ShopDto> updateShop(@PathVariable UUID id,@RequestBody ShopDto shopDto) {
        try{
            shopDto = shopService.updateShop(id, shopDto);
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/active/shop")
    public ResponseEntity<List<ShopDto>> activeShop() {
        try{
            List<ShopDto> shopDto = new ArrayList<>();
            shopDto = shopService.getActiveShop();
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/active/shops/category-id/{categoryId}")
    public ResponseEntity<List<ShopDto>> activeShopUsingCategoryId(@PathVariable UUID categoryId) {
        
            List<ShopDto> shopDto = new ArrayList<>();
            shopDto = shopService.activeShopUsingCategoryId(categoryId);
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        
    }

    @GetMapping("/shops")
    public ResponseEntity<List<ShopDto>> getAllShop() {
        try{
            List<ShopDto> shopDto = new ArrayList<>();
            shopDto = shopService.getActiveShop();
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/shops/{shopId}/{categoryId}")
    public ResponseEntity<List<ShopDto>> getAllShopByUsingShopUserIdAndCategoryId(@PathVariable UUID shopId,@PathVariable UUID categoryId) {
        try{
            List<ShopDto> shopDto = new ArrayList<>();
            shopDto = shopService.getAllShopsByShopUserIdAndCategoryId(shopId,categoryId);
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/shop/{id}")
    public ResponseEntity<ShopDto> getShopById(@PathVariable UUID id) {
        try{
            ShopDto shopDto = new ShopDto();
            shopDto = shopService.getShopById(id);
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

    @DeleteMapping("/shop/{id}")
    public ResponseEntity<ShopDto> deleteShop(@PathVariable UUID id) {
        try{
            ShopDto shopDto = new ShopDto();
            shopDto = shopService.updateShop(id, shopDto);
            return new ResponseEntity<>(shopDto,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // manage shopproduct

    @PostMapping("/shop_products")
    private ResponseEntity<ShopProductRequestDto> addShopProduct(@RequestBody ShopProductRequestDto shopProductRequestDto) throws Exception{
        try{
            shopProductRequestDto  = shopService.addShopProducts(shopProductRequestDto);
            // if(shopProductRequestDto != null){
                return new ResponseEntity<>(shopProductRequestDto,HttpStatus.CREATED);
            // }
            
        }catch(Exception e){
            throw e;
        }
        catch(Throwable t){
            throw t;
        }

        
    }

    @GetMapping("/shop_product/{id}")
    private ResponseEntity<ShopProductDto> getShopProductById(@PathVariable UUID id){
        try{
            ShopProductDto shopProductDto  = shopService.getShopProductById(id);
            if(shopProductDto != null){
                return new ResponseEntity<>(shopProductDto,HttpStatus.OK);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/shop_products")
    private ResponseEntity<List<ShopProductDto>> getActiveShopProducts(){
        try{
            List<ShopProductDto> shopProductDtos  = shopService.getAllActiveShopProduct();
            if(shopProductDtos != null){
                return new ResponseEntity<>(shopProductDtos,HttpStatus.OK);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/shop_product/{id}")
    private ResponseEntity<ShopProductDto> updateShopProduct(@PathVariable UUID id,@RequestBody ShopProductDto shopProductDto){
        try{
            shopProductDto  = shopService.editShopProduct(id,shopProductDto);
            if(shopProductDto != null){
                return new ResponseEntity<>(shopProductDto,HttpStatus.OK);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/shop_product/{id}")
    private ResponseEntity<ShopProductDto> deleteShopPrduct(@PathVariable UUID id){
        try{
            shopService.deleteShopProduct(id);
            
            
        }catch(Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
