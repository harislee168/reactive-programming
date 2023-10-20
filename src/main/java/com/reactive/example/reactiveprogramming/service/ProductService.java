package com.reactive.example.reactiveprogramming.service;

import com.reactive.example.reactiveprogramming.dto.ProductDto;
import com.reactive.example.reactiveprogramming.repository.ProductRepository;
import com.reactive.example.reactiveprogramming.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAll().map(MapUtils::productToDto);
    }

    public Mono<ProductDto> getProductByID(String id) {
        return productRepository.findById(id).map(MapUtils::productToDto);
    }

    public Flux<ProductDto> getProductByPriceRange(double min, double max) {
        return productRepository.findByPriceBetween(Range.closed(min, max))
                .map(MapUtils::productToDto);
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono.map(MapUtils::dtoToProduct)
                .flatMap(productRepository::insert)
                .map(MapUtils::productToDto);
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return productRepository.findById(id)
                .flatMap(productFromDB -> productDtoMono.map(MapUtils::dtoToProduct))
                .doOnNext(productFromMono -> productFromMono.setId(id))
                .flatMap(productRepository::save)
                .map(MapUtils::productToDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return productRepository.deleteById(id);
    }
}
