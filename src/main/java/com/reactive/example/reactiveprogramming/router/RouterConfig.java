package com.reactive.example.reactiveprogramming.router;

import com.reactive.example.reactiveprogramming.dto.CustomerDto;
import com.reactive.example.reactiveprogramming.handler.CustomerHandler;
import com.reactive.example.reactiveprogramming.handler.CustomerStreamHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperations(
            {
                    @RouterOperation(
                            path="/route/customer",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CustomerHandler.class,
                            beanMethod = "getAllCustomersWithoutSleep",
                            operation = @Operation(
                                    operationId = "getAllCustomersWithoutSleep",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Success",
                                                    content = @Content(schema=@Schema(
                                                            implementation = CustomerDto.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path="/route/customer/{id}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = CustomerHandler.class,
                            beanMethod = "getCustomerWithID",
                            operation = @Operation(
                                    operationId = "getCustomerWithID",
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name="id", description = "customer id")
                                    },
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Success",
                                                    content = @Content(schema = @Schema(
                                                            implementation = CustomerDto.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Customer not found with this ID"
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/route/customer/save",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = CustomerHandler.class,
                            beanMethod = "saveCustomer",
                            operation = @Operation(
                                    operationId = "saveCustomer",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Success",
                                                    content = @Content(schema = @Schema(
                                                            implementation = String.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = CustomerDto.class
                                            ))
                                    )
                            )
                    )
            }
    )
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/route/customer", customerHandler::getAllCustomersWithoutSleep)
                .GET("/route/customer/stream", customerStreamHandler::getAllCustomersStreamWithSleep)
                .GET("/route/customer/{id}", customerHandler::getCustomerWithID)
                .POST("/route/customer/save", customerHandler::saveCustomer)
                .build();
    }
}
