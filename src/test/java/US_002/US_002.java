package US_002;

import Utilities.Login;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_002 extends Login {

    public String countryID;

    @Test
    public void CreateACountry() {

        String countryName = randomFaker.address().country() + randomFaker.address().latitude();
        System.out.println("countryName = " + countryName);
        String countryCode = randomFaker.address().countryCode();

        Map<String, Object> newCountry = new HashMap<>();
        newCountry.put("name", countryName);
        newCountry.put("code", countryCode);
        newCountry.put("hasState", true);


        countryID =
                given()
                        .spec(reqSpec)
                        .body(newCountry)

                        .when()
                        .post("/school-service/api/countries")


                        .then()
                        .statusCode(201)
                        .extract().path("id");

        //System.out.println("countryID = " + countryID);
    }


}
