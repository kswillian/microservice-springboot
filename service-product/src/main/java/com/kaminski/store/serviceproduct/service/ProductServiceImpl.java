package com.kaminski.store.serviceproduct.service;

import com.kaminski.store.serviceproduct.entity.Category;
import com.kaminski.store.serviceproduct.entity.Product;
import com.kaminski.store.serviceproduct.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAll() {

        return productRepository.findAll();

    }

    @Override
    public Product getProduct(Long id) {

        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            return product.get();
        }
        return null;

    }

    @Override
    public Product create(Product product) {

        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);

    }

    @Override
    public Product update(Product product) {

        Product productDB = getProduct(product.getId());

        if(null == productDB){
            return null;
        }

        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCreateAt(product.getCreateAt());
        productDB.setPrice(product.getPrice());
        productDB.setStock(product.getStock());
        productDB.setCategory(product.getCategory());

        return productRepository.save(product);

    }

    @Override
    public Product delete(Long id) {

        Product productDB = getProduct(id);

        if(null == productDB){
            return null;
        }

        productDB.setStatus("DELETED");
        return productRepository.save(productDB);

    }

    @Override
    public List<Product> findByCategory(Category category) {

        return productRepository.findByCategory(category);

    }

    @Override
    public Product updateStock(Long id, Double quantity) {

        Product productDB = getProduct(id);

        if(null == productDB){
            return null;
        }

        Double newStock = productDB.getStock() + quantity;
        productDB.setStock(newStock);
        return productRepository.save(productDB);

    }

}
