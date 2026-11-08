package com.shop.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.shop.order.Order;
import com.shop.order.OrderItem;
import com.shop.order.OrderLine;
import com.shop.order.OrderService;
import com.shop.order.OrderStatus;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTests {
    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    OrderService service;

    @Test
    void placedOrderComesBackWithItsTotal() {
        given(service.place(List.of(new OrderItem(1, 2), new OrderItem(2, 1))))
                .willReturn(new Order(7, OrderStatus.NEW, List.of(
                        new OrderLine(1, "Keyboard", 4900, 2), new OrderLine(2, "Mouse", 2500, 1))));
        String order = """
                {"lines":[{"productId":1,"quantity":2},{"productId":2,"quantity":1}]}
                """;
        assertThat(mvc.post().uri("/api/orders").contentType(MediaType.APPLICATION_JSON).content(order))
                .hasStatus(HttpStatus.CREATED)
                .bodyJson()
                .hasPathSatisfying("$.status", value -> assertThat(value).isEqualTo("NEW"))
                .hasPathSatisfying("$.lines[0].subtotalInCents", value -> assertThat(value).isEqualTo(9800))
                .hasPathSatisfying("$.totalInCents", value -> assertThat(value).isEqualTo(12300));
    }

    @Test
    void emptyOrderIsRejected() {
        assertThat(mvc.post().uri("/api/orders").contentType(MediaType.APPLICATION_JSON).content("{\"lines\":[]}"))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson().extractingPath("$.errors.lines").isEqualTo("The order needs at least one line");
    }

    @Test
    void quantityMustBeAtLeastOne() {
        String order = """
                {"lines":[{"productId":1,"quantity":0}]}
                """;
        assertThat(mvc.post().uri("/api/orders").contentType(MediaType.APPLICATION_JSON).content(order))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson().extractingPath("$.errors['lines[0].quantity']").isEqualTo("Quantity must be at least 1");
    }
}
