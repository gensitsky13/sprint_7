package tests;

import client.OrderClient;
import config.BaseTest;

import org.junit.Test;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

@Epic("API Tests")
@Feature("Order List")
public class OrderListTest extends BaseTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверяем, что список заказов не пустой")
    public void ordersListIsNotEmpty() {
        getOrdersAndVerify();
    }

    @Step("Получаем список заказов и проверяем, что он не пустой")
    public void getOrdersAndVerify() {
        orderClient.getOrders()
                .statusCode(200)
                .body("orders", not(empty()));
    }
}