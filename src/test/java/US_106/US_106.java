package US_106;

import US_002.US_002;
import US_105.US_105;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class US_106 extends US_002 {

    public String studentGroupsID;
    Map<String, Object> newStudentGroups;

    @BeforeClass
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
        // System.out.println("studentGroupsID = " + studentGroupsID);
    }

    @Test
    public void CreateStudents() {
        String newStudents = "[\"657711ca8af7ce488ac6a628\", \"658ef7edcacea97f2a0cb060\", " +
                "\"658ef801cacea97f2a0cb072\", \"658ef815cacea97f2a0cb073\", " +
                "\"658ef829cacea97f2a0cb081\"]";

        given()
                .spec(reqSpec)
                .body(newStudents)
                .queryParam("page", 0)
                .queryParam("size", 10)

                .when()
                .post("/school-service/api/student-group/" + studentGroupsID + "/add-students")  // ?page=0&size=10
                .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "CreateStudents")
    public void ReadStudents() {

        given()
                .spec(reqSpec)
                .queryParam("page", 0)
                .queryParam("size", 10)

                .when()
                .get("/school-service/api/students/group/" + studentGroupsID)

                .then()
                .log().body()
                .statusCode(200);
    }


}
