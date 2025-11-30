package tests;

import client.OrderClient;
import model.Order;
import config.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient();

    private Order buildOrder(String[] colors) {
        return new Order(
                "Иван",
                "Иванов",
                "Москва, Тверская 1",
                "4",
                "+79990000001",
                5,
                "2025-12-10",
                "Позвонить за час",
                colors
        );
    }

    @Test
    public void canCreateOrderWithBlackColor() {
        Order order = buildOrder(new String[]{"BLACK"});

        orderClient.createOrder(order)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }

    @Test
    public void canCreateOrderWithGreyColor() {
        Order order = buildOrder(new String[]{"GREY"});

        orderClient.createOrder(order)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }

    @Test
    public void canCreateOrderWithBothColors() {
        Order order = buildOrder(new String[]{"BLACK", "GREY"});

        orderClient.createOrder(order)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", notNullValue());
    }
}