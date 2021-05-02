package com.kaminski.store.serviceproduct.service;

import com.kaminski.store.serviceproduct.entity.Category;
import com.kaminski.store.serviceproduct.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> listAll();

    Product getProduct(Long id);

    Product create(Product product);

    Product update(Product product);

    Product delete(Long id);

    List<Product> findByCategory(Category category);

    Product updateStock(Long id, Double quantity);

}
