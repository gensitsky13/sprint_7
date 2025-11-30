package tests;

import client.CourierClient;
import model.Courier;
import model.CourierCredentials;
import config.BaseTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest extends BaseTest {

    private final CourierClient courierClient = new CourierClient();
    private Courier testCourier;
    private Integer courierId;

    @Before
    public void createCourierForLogin() {
        String login = "login_" + System.currentTimeMillis();
        testCourier = new Courier(login, "password123", "Ivan");

        courierClient.createCourier(testCourier)
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        Response loginResponse = courierClient.loginCourier(
                new CourierCredentials(testCourier.getLogin(), testCourier.getPassword())
        );

        courierId = loginResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanLoginWithValidCredentials() {
        courierClient.loginCourier(
                        new CourierCredentials(testCourier.getLogin(), testCourier.getPassword())
                )
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue());
    }

    @Test
    public void cannotLoginWithWrongCredentials() {
        courierClient.loginCourier(
                        new CourierCredentials("wrongLogin", "wrongPass")
                )
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void cannotLoginWithoutLogin() {
        courierClient.loginCourier(
                        new CourierCredentials(null, testCourier.getPassword())
                )
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void cannotLoginWithoutPassword() {
        courierClient.loginCourier(
                        new CourierCredentials(testCourier.getLogin(), null)
                )
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}