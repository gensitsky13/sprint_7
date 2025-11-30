package tests;

import client.OrderClient;
import config.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    public void canGetOrdersList() {
        orderClient.getOrders()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("orders", notNullValue());
    }
}