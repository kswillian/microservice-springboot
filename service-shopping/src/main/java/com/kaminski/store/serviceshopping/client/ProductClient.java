package com.kaminski.store.serviceshopping.client;

import com.kaminski.store.serviceshopping.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
@RequestMapping("/products")
public interface ProductClient {

    @GetMapping(value = "/{id}")
    ResponseEntity<Product> getProduct(@PathVariable Long id);

    @GetMapping(value = "/{id}/stock")
    ResponseEntity<Product> updateStockProduct(@PathVariable Long id, @RequestParam Double quantity);

}
