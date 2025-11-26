package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrders() {
        return given()
                .when()
                .get(ORDERS_PATH)
                .then();
    }
}