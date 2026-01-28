package test.java.courier;

import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCredentials;
import org.junit.Test;
import test.java.BaseTest;

import static api.CourierApi.createCourier;
import static api.CourierApi.loginAndGetId;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseTest {

    @Test
    @DisplayName("Успешный логин курьера")
    public void testLoginCourierSuccess() {
        // Создаем курьера
        Courier courier = new Courier("login_success_" + System.currentTimeMillis(), "1234", "saske");
        createCourier(courier);

        // Получаем ID для удаления в tearDown
        String courierId = loginAndGetId(courier.getLogin(), courier.getPassword());

        // Тестируем логин
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), courier.getPassword());

        CourierApi.loginCourier(creds)
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин с неправильным паролем возвращает ошибку")
    public void testLoginWithWrongPassword() {
        // Создаем курьера
        Courier courier = new Courier("wrong_pass_" + System.currentTimeMillis(), "1234", "saske");
        createCourier(courier);

        // Получаем ID для удаления в tearDown
        Object courierId = loginAndGetId(courier.getLogin(), courier.getPassword());

        // Пытаемся залогиниться с неправильным паролем
        CourierCredentials wrongCreds = new CourierCredentials(courier.getLogin(), "wrong_password");

        CourierApi.loginCourier(wrongCreds)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    // ... остальные тесты аналогично
}