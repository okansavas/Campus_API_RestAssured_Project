package US_105;

import US_002.US_002;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_105 extends US_002 {

    public String studentGroupsID;
    Map<String, Object> newStudentGroups;

    @Test
    public void CreateAStudentGroups() {

        newStudentGroups = new HashMap<>();
        newStudentGroups.put("schoolId", schoolID);
        newStudentGroups.put("name", "Osman" + randomFaker.company().catchPhrase());
        newStudentGroups.put("description", randomFaker.lorem().sentence());
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
        System.out.println("studentGroupsID = " + studentGroupsID);
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

    @Test(dependsOnMethods = "CreateAStudentGroups")
    public void ReadTheStudentGroups() {

        given()
                .spec(reqSpec)

                .when()
                .get("/school-service/api/student-group/" + studentGroupsID)

                .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "ReadTheStudentGroups")
    public void UpdateTheStudentGroups() {

        Map<String, Object> updateStudentGroups = new HashMap<>();
        updateStudentGroups.put("id", studentGroupsID);
        updateStudentGroups.put("schoolId", schoolID);
        updateStudentGroups.put("name", randomFaker.book().genre());
        updateStudentGroups.put("description", randomFaker.commerce().productName());
        updateStudentGroups.put("active", true);
        updateStudentGroups.put("publicGroup", true);
        updateStudentGroups.put("showToStudent", true);

        given()
                .spec(reqSpec)
                .body(updateStudentGroups)

                .when()
                .put("/school-service/api/student-group/")

                .then()
                .statusCode(200)
                .log().body();
    }

    @Test(dependsOnMethods = "UpdateTheStudentGroups")
    public void DeleteStudentGroups() {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/student-group/" + studentGroupsID)

                .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "DeleteStudentGroups")
    public void DeleteStudentGroupsNegative() {

        given()
                .spec(reqSpec)

                .when()
                .delete("/school-service/api/student-group/" + studentGroupsID)

                .then()
                .statusCode(400);
    }
}
