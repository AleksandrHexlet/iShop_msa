package com.sprng.users.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprng.library.entity.ProductTrader;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.controller.TraderController;
import com.sprng.users.service.ProductTraderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;

@WebMvcTest(TraderController.class)
//@SpringBootTest(classes=TraderController.class)

public class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductTraderService productTraderService;

    ProductTrader productTraderIgor = new ProductTrader();

    ProductTrader productTraderZenya = new ProductTrader();

    @Test
    public void ProductTraderListSizeTwo() throws Exception {
        productTraderIgor.setName("Igor");
        Mockito.when(productTraderService.getAllTraderById(List.of(1, 2)))
                .thenReturn(List.of(productTraderIgor, productTraderZenya));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/trader/get/traders?id=1,2")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_Products.Read"),
                                new SimpleGrantedAuthority("SCOPE_Products.Write"))).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Igor")));
    }

    @Test
    public void ProductTraderRegistrationTrue() throws Exception {
        productTraderIgor.setName("Igor");
        productTraderIgor.setId(123);
        productTraderIgor.setTraderBill(BigDecimal.valueOf(123.89));

        Mockito.when(productTraderService.traderRegistration(Mockito.any(ProductTrader.class)))
//        Mockito.when(productTraderService.traderRegistration(Mockito.eq(productTraderIgor)))
                .thenReturn(productTraderIgor);
// это запросы к контроллеру. Либо с выделенным портом(интеграционные тесты)  либо подложные сервлеты(юнит тесты)
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/trader/registration")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_Products.Read"),
                                new SimpleGrantedAuthority("SCOPE_Products.Write")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productTraderIgor)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$", notNullValue()))
//                .andExpect(jsonPath("$.name", is("Igor")))
//                .andExpect(jsonPath("$.traderBill", greaterThan(0)));
    }

}
