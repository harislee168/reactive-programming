package com.reactive.example.reactiveprogramming.service;

import com.reactive.example.reactiveprogramming.dao.CustomerDao;
import com.reactive.example.reactiveprogramming.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<CustomerDto> getAllCustomers() {
        long start = System.currentTimeMillis();
        List<CustomerDto> customerDtoList =  customerDao.getAllCustomers();
        long end = System.currentTimeMillis();
        System.out.println("Time diff: "+ (end - start));
        return customerDtoList;
    }

    public Flux<CustomerDto> getAllCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<CustomerDto> customerDtoFlux =  customerDao.getAllCustomersStream();
        long end = System.currentTimeMillis();
        System.out.println("Time diff: "+ (end - start));
        return customerDtoFlux;
    }
}
