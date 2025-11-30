package tests;

import client.CourierClient;
import model.Courier;
import model.CourierCredentials;
import config.BaseTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierCreateTest extends BaseTest {

    private final CourierClient courierClient = new CourierClient();
    private Integer courierId;

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanBeCreated() {
        String login = "login_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "password123", "Ivan");

        Response createResponse = courierClient.createCourier(courier);

        createResponse.then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", equalTo(true));

        Response loginResponse = courierClient.loginCourier(
                new CourierCredentials(courier.getLogin(), courier.getPassword())
        );

        courierId = loginResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");
    }

    @Test
    public void cannotCreateDuplicateCourier() {
        String login = "dup_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "password123", "Ivan");

        courierClient.createCourier(courier)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", equalTo(true));

        // ✔ ФАКТИЧЕСКИЙ текст ошибки API:
        // "Этот логин уже используется. Попробуйте другой."
        courierClient.createCourier(courier)
                .then()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message",
                        equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        Courier courier = new Courier(null, "password123", "Ivan");

        courierClient.createCourier(courier)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void cannotCreateCourierWithoutPassword() {
        Courier courier = new Courier("nopass_" + System.currentTimeMillis(), null, "Ivan");

        courierClient.createCourier(courier)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message",
                        equalTo("Недостаточно данных для создания учетной записи"));
    }
}