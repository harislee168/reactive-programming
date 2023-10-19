package com.reactive.example.reactiveprogramming.controller;

import com.reactive.example.reactiveprogramming.dto.CustomerDto;
import com.reactive.example.reactiveprogramming.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping(value="/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtoList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerDtoList, HttpStatus.OK);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<CustomerDto>> getAllCustomersStream() {
        Flux<CustomerDto> customerDtoFlux = customerService.getAllCustomersStream();
        return new ResponseEntity<>(customerDtoFlux, HttpStatus.OK);
    }
}
