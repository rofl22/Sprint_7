

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import models.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierApiTest extends BaseTest {
    private CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Параметризованный тест создания курьера - вариант 1")
    public void testCreateCourierParameterized1() {
        String login = "ninja_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "1234", "saske");

        courierApi.createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierApi.getCourierId(
                new CourierCredentials(login, "1234")
        );
    }

    @Test
    @DisplayName("Параметризованный тест создания курьера - вариант 2")
    public void testCreateCourierParameterized2() {
        String login = "samurai_" + System.currentTimeMillis();
        Courier courier = new Courier(login, "5678", "naruto");

        courierApi.createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierApi.getCourierId(
                new CourierCredentials(login, "5678")
        );
    }

    @After
    public void cleanup() {
        super.cleanupCourier();
    }
}