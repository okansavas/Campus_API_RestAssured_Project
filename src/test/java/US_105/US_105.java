package US_105;

import US_002.US_002;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_105 extends US_002 {

    String studentGroupsID;
    Map<String, Object> newStudentGroups;

    @Test
    public void CreateAStudentGroups() {

        newStudentGroups = new HashMap<>();
        newStudentGroups.put("schoolId", schoolID);
        newStudentGroups.put("name", "Osman" + randomFaker.idNumber());
        newStudentGroups.put("description", randomFaker.address().latitude());
        newStudentGroups.put("active", true);
        newStudentGroups.put("publicGroup", true);
        newStudentGroups.put("showToStudent", true);


        studentGroupsID =
                given()
                        .spec(reqSpec)
                        .body(newStudentGroups)

                        .when()
                        .post("/school-service/api/student-group")


                        .then()
                        .statusCode(201)
                        .extract().path("id");
        //System.out.println("studentGroupsID = " + studentGroupsID);
    }

    @Test(dependsOnMethods = "CreateAStudentGroups")
    public void CreateAStudentGroupsNegative() {

        given()
                .spec(reqSpec)
                .body(newStudentGroups)

                .when()
                .post("/school-service/api/student-group")

                .then()
                .statusCode(400);
    }

}
