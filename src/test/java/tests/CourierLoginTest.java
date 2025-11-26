package tests;

import client.CourierClient;
import config.BaseTest;

import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;

@Epic("API Tests")
@Feature("Courier Login")
public class CourierLoginTest extends BaseTest {

    private final CourierClient courierClient = new CourierClient();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что курьер может успешно войти в систему после регистрации")
    public void courierCanLogin() {

        Courier courier = new Courier(
                "login_" + System.currentTimeMillis(),
                "password123",
                "Vasya"
        );

        createCourier(courier);
        authorizeCourier(courier);
    }

    @Step("Создаём курьера перед логином")
    public void createCourier(Courier courier) {
        ValidatableResponse response =
                courierClient.createCourier(courier);

        response
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Step("Логинимся под курьером и проверяем успешную авторизацию")
    public void authorizeCourier(Courier courier) {

        CourierCredentials creds = CourierCredentials.from(courier);

        ValidatableResponse response =
                courierClient.login(creds);

        response
                .statusCode(200)
                .body("id", notNullValue());
    }
}
