package com.reactive.example.reactiveprogramming.dao;

import com.reactive.example.reactiveprogramming.dto.CustomerDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    public List<CustomerDto> getAllCustomers() {
        return IntStream.rangeClosed(1,15)
                .peek(integer -> System.out.println("processing: " + integer))
                .peek(this::sleep)
                .mapToObj(integer -> new CustomerDto(integer, "Customer " + integer))
                .toList();
    }

    public Flux<CustomerDto> getAllCustomersStream() {
        return Flux.range(1, 15)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(integer -> System.out.println("processing in stream: " + integer))
                .map(integer -> new CustomerDto(integer, "Customer " + integer));
    }

    private void sleep(int integer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
