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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;

//@WebMvcTest(TraderController.class)
//@SpringBootTest(classes=TraderController.class)
@SpringBootTest
@AutoConfigureMockMvc
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
                .thenReturn(List.of(productTraderIgor,productTraderZenya));

        mockMvc.perform(MockMvcRequestBuilders.get("/get/traders?id=1,2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Igor")));
    }

 @Test
    public void ProductTraderRegistrationTrue() throws Exception {
     productTraderIgor.setName("Igor");
     productTraderIgor.setTraderBill(BigDecimal.valueOf(123.89));
        Mockito.when(productTraderService.traderRegistration(productTraderIgor))
                .thenReturn(productTraderIgor);

         mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                 .accept(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(productTraderIgor)))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$", notNullValue()))
                 .andExpect(jsonPath("$.name" , is("Igor")))
                 .andExpect(jsonPath("$.traderBill",greaterThan(0) ));
 }

}
