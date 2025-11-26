package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.Courier;
import model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    private RequestSpecification spec() {
        return given()
                .header("Content-type", "application/json");
    }

    @Step("Создать курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(CourierCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(COURIER_PATH + "/login")
                .then();
    }

    @Step("Удалить курьера id={id}")
    public ValidatableResponse delete(Integer id) {
        return spec()
                .when()
                .delete(COURIER_PATH + "/" + id)
                .then();
    }
}
