package com.reactive.example.reactiveprogramming.repository;

import com.reactive.example.reactiveprogramming.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    public Flux<Product> findByPriceBetween(Range<Double> priceRange);
}
