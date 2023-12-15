package com.sprng.users.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprng.library.entity.ProductTrader;
import com.sprng.users.service.ProductTraderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductTraderIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Transactional
    @Test
    public void productTraderTest() throws JsonProcessingException, Exception {
        ProductTrader productTraderBill = new ProductTrader();
        productTraderBill.setName("Bill");
        productTraderBill.setUserName("BillUserNAme123");
        productTraderBill.setCityStorage("MSK");
        productTraderBill.setPassword("345");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/trader/registration")
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_Products.Read"),
                                        new SimpleGrantedAuthority("SCOPE_Products.Write")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productTraderBill)))
                .andExpect(status().isCreated());

    }
}


//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/trader/get/traders?id=1,2")
//                .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_Products.Read"),
//                new SimpleGrantedAuthority("SCOPE_Products.Write")))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("Igor")));
//                }