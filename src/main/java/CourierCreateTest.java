import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierCreateTest extends BaseTest {

    @Test
    @DisplayName("Успешное создание курьера")
    public void testCreateCourierSuccess() {
        Courier courier = new Courier("ninja_" + System.currentTimeMillis(), "1234", "saske");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");

        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Сохраняем ID для удаления в tearDown
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword());
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров возвращает ошибку")
    public void testCreateDuplicateCourier() {
        String uniqueLogin = "duplicate_" + System.currentTimeMillis();
        Courier courier = new Courier(uniqueLogin, "1234", "saske");

        // Создаем первого курьера
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201);

        // Сохраняем ID для удаления
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword()); // ПРАВИЛЬНО
        //courierId = createCourier(courier);

        // Пытаемся создать такого же курьера
        given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина возвращает ошибку")
    public void testCreateCourierWithoutLogin() {
        Courier invalidCourier = new Courier(null, "1234", "saske");

        given()
                .header("Content-type", "application/json")
                .body(invalidCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля возвращает ошибку")
    public void testCreateCourierWithoutPassword() {
        Courier invalidCourier = new Courier("ninja", null, "saske");

        given()
                .header("Content-type", "application/json")
                .body(invalidCourier)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без имени возвращает успех (имя необязательное)")
    public void testCreateCourierWithoutFirstName() {
        Courier courier = new Courier("noname_" + System.currentTimeMillis(), "1234", null);

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Сохраняем ID для удаления
        courierId = loginAndGetId(courier.getLogin(), courier.getPassword());
    }
}