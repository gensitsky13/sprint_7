package client;

import io.restassured.response.Response;
import model.Order;

import static config.ApiEndpoints.*;
import static io.restassured.RestAssured.given;

public class OrderClient {

    public Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(ORDERS);
    }

    public Response getOrders() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS);
    }
}