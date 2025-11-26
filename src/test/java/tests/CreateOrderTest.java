package tests;

import client.OrderClient;
import config.BaseTest;
import model.Order;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

@Epic("API Tests")
@Feature("Order Creation")
public class CreateOrderTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем успешное создание заказа и получение track номера")
    public void canCreateOrder() {

        Order order = Order.defaultOrder();

        createOrder(order);
    }

    @Step("Создаём заказ и проверяем статус 201 и наличие track")
    public void createOrder(Order order) {

        orderClient.createOrder(order)
                .statusCode(201)
                .body("track", notNullValue());
    }
}