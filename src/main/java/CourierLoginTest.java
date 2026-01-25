import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import models.CourierCredentials;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest extends BaseTest {

    @Test
    @DisplayName("Успешный логин курьера")
    public void testLoginCourierSuccess() {
        // Создаем курьера
        Courier courier = new Courier("login_success_" + System.currentTimeMillis(), "1234", "saske");
        createCourier(courier);

        // Получаем ID для удаления в tearDown
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword());

        // Тестируем логин
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), courier.getPassword());

        given()
                .header("Content-type", "application/json")
                .body(creds)
                .when()
                .post("/api/v1/courier/login")
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
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword());

        // Пытаемся залогиниться с неправильным паролем
        CourierCredentials wrongCreds = new CourierCredentials(courier.getLogin(), "wrong_password");

        given()
                .header("Content-type", "application/json")
                .body(wrongCreds)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин без пароля возвращает ошибку")
    public void testLoginWithoutPassword() {
        // Создаем курьера
        Courier courier = new Courier("no_password_" + System.currentTimeMillis(), "1234", "saske");
        createCourier(courier);

        // Получаем ID для удаления в tearDown
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword());

        // Пытаемся залогиниться без пароля
        CourierCredentials noPasswordCreds = new CourierCredentials(courier.getLogin(), null);

        given()
                .header("Content-type", "application/json")
                .body(noPasswordCreds)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин без логина возвращает ошибку")
    public void testLoginWithoutLogin() {
        CourierCredentials noLoginCreds = new CourierCredentials(null, "1234");

        given()
                .header("Content-type", "application/json")
                .body(noLoginCreds)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин несуществующего курьера возвращает ошибку")
    public void testLoginNonExistentCourier() {
        CourierCredentials nonexistentCreds = new CourierCredentials("nonexistent_" + System.currentTimeMillis(), "1234");

        given()
                .header("Content-type", "application/json")
                .body(nonexistentCreds)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин с пустым логином возвращает ошибку")
    public void testLoginWithEmptyLogin() {
        CourierCredentials emptyLoginCreds = new CourierCredentials("", "1234");

        given()
                .header("Content-type", "application/json")
                .body(emptyLoginCreds)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин с пустым паролем возвращает ошибку")
    public void testLoginWithEmptyPassword() {
        // Создаем курьера
        Courier courier = new Courier("empty_pass_" + System.currentTimeMillis(), "1234", "saske");
        createCourier(courier);

        // Получаем ID для удаления в tearDown
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword());

        // Пытаемся залогиниться с пустым паролем
        CourierCredentials emptyPasswordCreds = new CourierCredentials(courier.getLogin(), "");

        given()
                .header("Content-type", "application/json")
                .body(emptyPasswordCreds)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}