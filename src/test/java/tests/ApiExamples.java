package tests;

import endpoints.BuyEndpoint;
import endpoints.LoginEndpoint;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.testng.AssertJUnit.assertTrue;

public class ApiExamples {

    @Test
    public void loginIntoApi() {
        String token =

                given()

                        .body(new Credentials("Pedro", "Pedro"))
                        .header("Content-Type", ContentType.JSON)
                        .when()
                        .post("http://localhost:5000/api/login")
                        .peek()
                        .then()
                        .statusCode(200)
                        .body("token", notNullValue())
                        .extract()
                        .body().jsonPath().get("token");

        System.out.printf("Token:" + token);

    }

    @Test
    public void loginHappierPath(){
        AuthInfo authInfo= LoginEndpoint.login("Pedro","Pedro");
        System.out.println(authInfo.getToken());

    }

    @Test
    public void buyHappierPath(){

        AuthInfo authInfo= LoginEndpoint.login("Pedro","Pedro");
        BuyResponseBody buyResponseBody = BuyEndpoint.buyStock("IBM",5,authInfo);
        System.out.println(buyResponseBody.getMessage());
    }


    @Test
    public void PortFolioTest(){

        AuthInfo token = getToken();
        PortfolioPayload pp =
                given()
                        .baseUri("http://localhost:5000")
                        .header("x-access-token", token.getToken())
                        .when()
                        .get("/api/portfolio")
                        .peek()
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("cash", notNullValue())
                        .body("position", notNullValue())
                        .body("stocks", notNullValue())
//            .body("stocks[0].symbol", equalTo("AMZN"))
//            .body("stocks[1].symbol", equalTo("MSFT"))
//            .body("stocks.symbol", containsInAnyOrder("MSFT", "AMZN"))
                        .extract()
                        .as(PortfolioPayload.class);


    }

    @Test
    public void buyHappyPath() {
        AuthInfo token = getToken();

        given()
                .header("x-access-token", token.getToken())
                .contentType(ContentType.JSON)
                .body(new BuyBody("IBM", 5))
                .baseUri("http://localhost:5000")
                .when()
                .post("/api/buy")
                .peek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", notNullValue())
                .body("message", equalTo("You bought 5 share(s) of IBM."));

    }

    @Test
    public void sellHappyPath() {
        AuthInfo token = getToken();

        given()
                .header("x-access-token", token.getToken())
                .contentType(ContentType.JSON)
                .body(new BuyBody("IBM", 5))
                .baseUri("http://localhost:5000")
                .when()
                .post("/api/sell")
                .peek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("message", notNullValue())
                .body("message", equalTo("You sold 5 share(s) from IBM"))
                .contentType(ContentType.JSON)
                .body("message", containsString("5"));

    }

    @Test
    public void getQuoteHappyPath() {

        AuthInfo tokenBody = getToken();
        QuoteResponseBody quote =

                given()
                        .header("x-access-token", tokenBody.getToken())
                        .contentType(ContentType.JSON)
                        .body(new QuoteBody("MSFT"))
                        .baseUri("http://localhost:5000")
                        .when()
                        .post("/api/quote")
                        .peek()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .body("name", equalTo("Microsoft Corporation"))
                        .body("price", notNullValue())
                        .body("symbol", notNullValue())
                        .body("symbol", equalTo("MSFT"))
                        .extract()
                        // el método "as" extrae la información de la clase QuoteResponseBody
                        .as(QuoteResponseBody.class);
        assertTrue(quote.getName().equalsIgnoreCase("Microsoft Corporation"));
        assertTrue(quote.getSymbol().equalsIgnoreCase("MSFT"));

    }

    public AuthInfo getToken () {

        return
        given()
                .baseUri("http://localhost:5000")
                .contentType(ContentType.JSON)
                .body(new Credentials("Pedro", "Pedro"))
                .when()
                .post("/api/login")
                .peek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("token", notNullValue())
                .extract()
                .as(AuthInfo.class);

    }
}


