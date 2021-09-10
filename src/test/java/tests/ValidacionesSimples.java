package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ValidacionesSimples {

    public static final String url = "http://localhost:5000";

    @Test
    public void getLoginPage() {
        RestAssured.get(url + "/login")
                .peek()
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.HTML);

    }

    @Test
    public void getRegister() {
        RestAssured.get(url + "/register")
                .peek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .contentType(ContentType.HTML);

    }

    @Test
    public void getPageInexistente() {
        RestAssured.get(url + "/inexistente")
                .peek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .contentType(ContentType.HTML)
                .and()
                .header("Server", "Werkzeug/1.0.1 Python/3.8.11");

    }

    //Aserciones con Matchers
    @Test
    public void getBuyNoLogin() {
        RestAssured.get(url + "/buy")
                .peek()
                .then()
                .assertThat()
                .header("Set-Cookie", containsString("session=; "))
                .body(containsString("Log In"))
                .body(not(containsString("Log in")))
                .statusCode(lessThan(300)) // que sea menor al código 300
                .statusCode(equalTo(200));


    }

    @Test
    public void loginIntoPage() {

        given()
                .formParam("username", "Pedro")
                .formParam("password", "Pedro")
                .when()
                .post("http://localhost:5000/login")
                .peek()
                .then()
                .statusCode(HttpStatus.SC_MOVED_TEMPORARILY)
                .contentType(ContentType.HTML)
                .header("Set-Cookie", not(startsWith("session=;")));

    }
}