package com.reactive.example.reactiveprogramming.controller;

import com.reactive.example.reactiveprogramming.dto.ProductDto;
import com.reactive.example.reactiveprogramming.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Flux<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value="/{id}")
    public Mono<ProductDto> getProductByID(@PathVariable String id) {
        return productService.getProductByID(id);
    }

    @GetMapping(value="/pricerange")
    public Flux<ProductDto> getProductByPriceRange(@RequestParam(value="min") double min,
                                                   @RequestParam(value="max") double max) {
        return productService.getProductByPriceRange(min, max);
    }

    @PostMapping
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return productService.saveProduct(productDtoMono);
    }

    @PutMapping(value="/update/{id}")
    public Mono<ProductDto> updateProduct(@PathVariable String id,
                                          @RequestBody Mono<ProductDto> productDtoMono) {
        return productService.updateProduct(id, productDtoMono);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}
