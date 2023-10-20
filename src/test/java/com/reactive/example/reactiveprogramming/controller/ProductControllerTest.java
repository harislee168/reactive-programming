package com.reactive.example.reactiveprogramming.controller;

import com.reactive.example.reactiveprogramming.dto.ProductDto;
import com.reactive.example.reactiveprogramming.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(value=ProductController.class)
public class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ProductService productService;

    @Test
    public void saveProductTest() {
        Mono<ProductDto> productDtoMono = Mono.just(
                new ProductDto("id888", "Iphone 13", 130, 1300));
        Mockito.when(productService.saveProduct(productDtoMono)).thenReturn(productDtoMono);
        webTestClient.post().uri("/product")
                .body(productDtoMono, ProductDto.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllProductsTest() {
        Flux<ProductDto> productDtoFlux = Flux.just(
                new ProductDto("id888", "Iphone 13", 130, 1300),
                new ProductDto("id999", "Iphone 14", 140, 1400)
        );
        Mockito.when(productService.getAllProducts()).thenReturn(productDtoFlux);
        Flux<ProductDto> responseBody = webTestClient.get().uri("/product")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectNext(new ProductDto("id888", "Iphone 13", 130, 1300))
                .expectNext(new ProductDto("id999", "Iphone 14", 140, 1400))
                .verifyComplete();
    }

    @Test
    public void getProductByIDTest() {
        Mono<ProductDto> productDtoMono = Mono.just(
                new ProductDto("id888", "Iphone 13", 130, 1300));
        Mockito.when(productService.getProductByID(Mockito.anyString())).thenReturn(productDtoMono);

        Flux<ProductDto> productDtoFlux = webTestClient.get().uri("/product/id188")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(productDtoFlux)
                .expectNextMatches(productDto -> productDto.getId().equals("id888"))
                .verifyComplete();
    }

    @Test
    public void updateProductTest() {
        Mono<ProductDto> productDtoMono = Mono.just(
                new ProductDto("id888", "Iphone 13", 130, 1300));

        Mockito.when(productService.updateProduct("id888", productDtoMono)).thenReturn(productDtoMono);

        webTestClient.put().uri("/product/update/id888")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void deleteProductTest() {
        Mockito.when(productService.deleteProduct(Mockito.anyString())).thenReturn(Mono.empty());
        webTestClient.delete().uri("/product/delete/id888")
                .exchange()
                .expectStatus().isOk();
    }
}
