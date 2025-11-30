package config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.Before;

public class BaseTest {

    @Before
    public void setUpBase() {
        RestAssured.baseURI = ApiEndpoints.BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }
}