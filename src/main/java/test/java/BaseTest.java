package test.java;

import api.CourierApi;
import models.Courier;
import org.junit.After;
import org.junit.Before;

public class BaseTest {

    protected String courierId;

    @Before
    public void setUp() {
        // Base URI уже установлен в ApiClient статическим блоком
    }

    @After
    public void tearDown() {
        // Удаление курьера после тестов, если он был создан
        if (courierId != null) {
            CourierApi.deleteCourier(courierId)
                    .then()
                    .statusCode(200);
        }
    }

    protected String createCourierAndGetId(Courier courier) {
        return CourierApi.createCourierAndGetId(courier);
    }

    protected void createCourier(Courier courier) {
        CourierApi.createCourier(courier)
                .then()
                .statusCode(201);
    }

    protected String loginAndGetId(String login, String password) {
        return CourierApi.loginAndGetId(login, password);
    }
}