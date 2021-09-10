package endpoints;

import io.restassured.http.ContentType;
import pojo.AuthInfo;
import pojo.Credentials;

import static io.restassured.RestAssured.given;

public class LoginEndpoint {

    public static AuthInfo login(String username, String password) {

        AuthInfo authInfo =

                given()
                        .baseUri("http://localhost:5000")
                        .contentType(ContentType.JSON)
                        .body(new Credentials(username, password))
                        .when()
                        .post("/api/login")
                        .peek()
                        .then()
                        .statusCode(200)
                        .extract()
                        .as(AuthInfo.class);
        return authInfo;

    }
}
