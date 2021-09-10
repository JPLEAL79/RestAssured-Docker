package endpoints;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import pojo.AuthInfo;
import pojo.BuyBody;
import pojo.BuyResponseBody;

import static io.restassured.RestAssured.given;

public class BuyEndpoint {

    public static BuyResponseBody buyStock(String symbol,int qty ,AuthInfo authInfo){
        return
        given()
                .header("x-access-token", authInfo.getToken())
                .body(new BuyBody(symbol,qty))
                .contentType(ContentType.JSON)
                .baseUri("http://localhost:5000")
                .when()
                .post("/api/buy")
                .peek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType(ContentType.JSON)
                .extract()
                .as(BuyResponseBody.class);

    }

}
