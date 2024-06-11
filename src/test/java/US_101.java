import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class US_101 {

    RequestSpecification reqSpec;

    @BeforeClass
    public void ValidLogin() {
        baseURI = "https://test.mersys.io";

        Map<String, String> userCredentials = new HashMap<>();
        userCredentials.put("username", "turkeyts");
        userCredentials.put("password", "TechnoStudy123");
        userCredentials.put("rememberMe", "true");

        Cookies responseCookies =
                given()
                        .body(userCredentials)
                        .contentType(ContentType.JSON)

                        .when()
                        .post("/auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();

        System.out.println("responseCookies = " + responseCookies);

        reqSpec = new RequestSpecBuilder()
                .addCookies(responseCookies)
                .setContentType(ContentType.JSON)
                .build();
    }




}
