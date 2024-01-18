package com.sprng.orderService.service;

import com.sprng.orderService.model.CustomerOrder;
import com.sprng.orderService.model.OrderDataDTO;
import com.sprng.orderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository;
    ClientProductService clientProductService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientProductService clientProductService) {
        this.orderRepository = orderRepository;
        this.clientProductService = clientProductService;
    }


    private void saveCustomerOrderInDB(List<OrderDataDTO> orderDataListFromClient, JwtAuthenticationToken token) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderDataDTO(orderDataListFromClient);
        customerOrder.setCreateOrder(LocalDateTime.now());
        customerOrder.setStatusOrder(CustomerOrder.StatusOrder.NEW);
        customerOrder.setUserNameCustomer(token.getToken().getSubject());
        orderRepository.save(customerOrder);
    }

    public List<OrderDataDTO> createNewOrder(List<OrderDataDTO> orderDataListFromClient, JwtAuthenticationToken token) {
        clientProductService.getCountProduct(orderDataListFromClient)
                .doOnNext(orderDataFromDB -> {
                    orderDataListFromClient.stream()
                            .peek(orderDataFromClient -> {
                                if (orderDataFromClient.getIdProduct() == orderDataFromDB.getIdProduct()) {
                                    orderDataFromClient.setIdProduct(orderDataFromClient.getIdProduct());
                                    if (orderDataFromClient.getCountProduct() > orderDataFromDB.getCountProduct()) {
                                        orderDataFromClient.setCountProduct(orderDataFromDB.getCountProduct());
                                    }
                                    orderDataFromClient.setCountProduct(orderDataFromClient.getCountProduct());
                                }

                            })
                            .toList();
                });
        saveCustomerOrderInDB(orderDataListFromClient, token);
        return orderDataListFromClient;
    }
}
