package com.reactive.example.reactiveprogramming.handler;

import com.reactive.example.reactiveprogramming.dao.CustomerDao;
import com.reactive.example.reactiveprogramming.dto.CustomerDto;
import com.reactive.example.reactiveprogramming.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerHandler(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Mono<ServerResponse> getAllCustomersWithoutSleep(ServerRequest request) {
        Flux<CustomerDto> customerDtoFlux = customerDao.getAllCustomersStreamWithoutSleep();
        return ServerResponse.ok().body(customerDtoFlux, CustomerDto.class);
    }

    public Mono<ServerResponse> getCustomerWithID(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<CustomerDto> customerDtoMono = customerDao.getAllCustomersStreamWithoutSleep()
                .filter(customer -> customer.getId() == id)
                .next()
                .switchIfEmpty(Mono.error(
                        new CustomerException("Customer not found with id: " + id)));
        return ServerResponse.ok().body(customerDtoMono, CustomerDto.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<CustomerDto> customerDtoMono = request.bodyToMono(CustomerDto.class);
        Mono<String> monoStr =  customerDtoMono.map(customerDto -> customerDto.getId() + " : " + customerDto.getName());
//        customerDtoMono.map(customerRepository::save);
        return ServerResponse.ok().body(monoStr, String.class);
    }
}
