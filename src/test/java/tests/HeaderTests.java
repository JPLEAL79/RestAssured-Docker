package tests;import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;


public class HeaderTests {

    public static final String baseURL = "http://vamonos-finance.herokuapp.com";

    @Test
    public void getLoginPage() {
        RestAssured.get(baseURL + "/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(equalTo(200))
                .statusCode(anyOf(equalTo(200), equalTo(201)))
                .contentType(ContentType.HTML);


    }

    @Test
    public void getBuyPageNoLogin2() {
        RestAssured.get(baseURL + "/buy")
                .peek()
                .then()
                .assertThat()
                .statusCode(equalTo(200))
                .and()
                .contentType(is("text/html; charset=utf-8"))
                .and()
                .header("Set-Cookie", containsStringIgnoringCase("session=;"));
    }

}
