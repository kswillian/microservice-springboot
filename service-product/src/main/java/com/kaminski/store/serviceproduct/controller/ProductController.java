package com.kaminski.store.serviceproduct.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaminski.store.serviceproduct.entity.Category;
import com.kaminski.store.serviceproduct.entity.Product;
import com.kaminski.store.serviceproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(
            @RequestParam(name = "categoryId", required = false) Long categoryId){

        List<Product> products = new ArrayList<>();

        if(categoryId == null){
            products = productService.listAll();

            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }

        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());

            if(products.isEmpty()){
                return ResponseEntity.noContent().build();
            }

        }

        return ResponseEntity.ok(products);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){

        Product product = productService.getProduct(id);

        if(product == null){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product, BindingResult result){

        if(result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(result));
        }

        Product productCreate = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){

        product.setId(id);
        Product productUpdated = productService.update(product);

        if(productUpdated == null){
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(productUpdated);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){

        Product product = productService.delete(id);

        if(product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }


    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(
            @PathVariable Long id, @RequestParam Double quantity){

        Product product = productService.updateStock(id, quantity);

        if(product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult bindingResult){

        List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder().code("01").messages(errors).build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = objectMapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;

    }
}
