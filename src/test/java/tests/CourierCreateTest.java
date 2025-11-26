package tests;

import client.CourierClient;
import config.BaseTest;

import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierCredentials;

import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.runner.RunWith;
import io.qameta.allure.junit4.AllureJunit4;
import org.junit.runner.notification.RunListener;



@Epic("API Tests")
@Feature("Courier Creation")
public class CourierCreateTest extends BaseTest {

    private final CourierClient courierClient = new CourierClient();
    private Integer courierId;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверяем, что курьер может быть успешно создан и затем авторизован")
    public void courierCanBeCreated() {

        Courier courier = new Courier(
                "login_" + System.currentTimeMillis(),
                "password123",
                "Vasya"
        );

        createCourier(courier);
        loginCourier(courier);

    }

    @Step("Создаём курьера и проверяем статус 201")
    public void createCourier(Courier courier) {
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        createResponse
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Step("Авторизуем курьера и сохраняем courierId")
    public void loginCourier(Courier courier) {

        CourierCredentials creds = new CourierCredentials(
                courier.getLogin(),
                courier.getPassword()
        );

        ValidatableResponse loginResponse = courierClient.login(creds);

        courierId = loginResponse
                .statusCode(200)
                .extract()
                .path("id");
    }

    @After
    @Step("Удаляем тестового курьера из системы")
    public void deleteCourier() {
        if (courierId != null) {
            courierClient.delete(courierId);
        }
    }
}