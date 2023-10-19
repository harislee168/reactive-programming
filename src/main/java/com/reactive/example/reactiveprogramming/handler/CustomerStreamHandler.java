package com.reactive.example.reactiveprogramming.handler;

import com.reactive.example.reactiveprogramming.dao.CustomerDao;
import com.reactive.example.reactiveprogramming.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerStreamHandler(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Mono<ServerResponse> getAllCustomersStreamWithSleep(ServerRequest request) {
        Flux<CustomerDto> customerDtoFlux = customerDao.getAllCustomersStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerDtoFlux, CustomerDto.class);
    }
}
