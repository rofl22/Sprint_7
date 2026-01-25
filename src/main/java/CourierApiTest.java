import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
//import io.restassured.response.Response;
import models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CourierApiTest {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private Courier courier;
    private String courierId;

    public CourierApiTest(Courier courier) {
        this.courier = courier;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {new Courier("ninja_" + System.currentTimeMillis(), "1234", "saske")},
                {new Courier("samurai_" + System.currentTimeMillis(), "5678", "naruto")}
        };
    }

    @After
    public void tearDown() {
        // Удаление курьера после тестов, если он был создан
        if (courierId != null) {
            given()
                    .delete("/api/v1/courier/" + courierId)
                    .then()
                    .statusCode(200);
        }
    }
}