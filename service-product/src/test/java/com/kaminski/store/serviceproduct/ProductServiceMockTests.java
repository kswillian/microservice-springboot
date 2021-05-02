package com.kaminski.store.serviceproduct;

import com.kaminski.store.serviceproduct.entity.Category;
import com.kaminski.store.serviceproduct.entity.Product;
import com.kaminski.store.serviceproduct.repository.ProductRepository;
import com.kaminski.store.serviceproduct.service.ProductService;
import com.kaminski.store.serviceproduct.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTests {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        productService = new ProductServiceImpl(productRepository);

        Product product01 = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("")
                .stock(10D)
                .price(1240.90D)
                .status("CREATED")
                .createAt(new Date()).build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(product01));

    }

    @Test
    public void whenValidGetId_thenReturnProduct(){
        Product founds = productService.getProduct(1L);
        Assertions.assertThat(founds.getName()).isEqualTo("computer");
    }
}
