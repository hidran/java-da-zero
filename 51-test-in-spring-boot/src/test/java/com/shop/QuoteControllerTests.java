package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
class QuoteControllerTests {
    @Autowired
    MockMvc mvc;
    @MockitoBean
    ShippingService shipping;

    @Test
    void mapsRequestAndDelegatesToService() throws Exception {
        when(shipping.fee(2000)).thenReturn(490);
        mvc.perform(get("/shipping").param("subtotalInCents", "2000"))
                .andExpect(status().isOk()).andExpect(content().string("490"));
        verify(shipping).fee(2000);
    }

    @Test
    void rejectsMissingParameter() throws Exception {
        mvc.perform(get("/shipping")).andExpect(status().isBadRequest());
        verifyNoInteractions(shipping);
    }
}
