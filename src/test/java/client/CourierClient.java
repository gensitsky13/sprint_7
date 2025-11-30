package client;

import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

import static config.ApiEndpoints.*;
import static io.restassured.RestAssured.given;

public class CourierClient {

    public Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(COURIER);
    }

    public Response loginCourier(CourierCredentials creds) {
        return given()
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post(COURIER_LOGIN);
    }

    public Response deleteCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER + "/" + id);
    }
}