package com.reactive.example.reactiveprogramming.router;

import com.reactive.example.reactiveprogramming.handler.CustomerHandler;
import com.reactive.example.reactiveprogramming.handler.CustomerStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    private final CustomerHandler customerHandler;
    private final CustomerStreamHandler customerStreamHandler;

    @Autowired
    public RouterConfig(CustomerHandler customerHandler, CustomerStreamHandler customerStreamHandler) {
        this.customerHandler = customerHandler;
        this.customerStreamHandler = customerStreamHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/route/customer", customerHandler::getAllCustomersWithoutSleep)
                .GET("/route/customer/stream", customerStreamHandler::getAllCustomersStreamWithSleep)
                .GET("route/customer/{id}", customerHandler::getCustomerWithID)
                .POST("route/customer/save", customerHandler::saveCustomer)
                .build();
    }
}
