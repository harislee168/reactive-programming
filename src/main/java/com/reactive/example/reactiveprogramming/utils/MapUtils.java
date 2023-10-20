package com.reactive.example.reactiveprogramming.utils;

import com.reactive.example.reactiveprogramming.dto.ProductDto;
import com.reactive.example.reactiveprogramming.entity.Product;
import org.springframework.beans.BeanUtils;

public class MapUtils {

    public static ProductDto productToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToProduct(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
