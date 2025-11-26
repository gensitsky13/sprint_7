package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    private RequestSpecification spec() {
        return given()
                .header("Content-type", "application/json");
    }

    @Step("Создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get(ORDERS_PATH)
                .then();
    }
}

