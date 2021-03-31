import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {
    public static final String HOST = "https://jsonplaceholder.typicode.com";
    public static final String RESOURCE = "/posts";

    RequestSpecification request = given()
            .baseUri(HOST + RESOURCE)
            .header("Language", "en");

    @Test
    public void getResponseByCorrectUserId() {
        request.when()
                .get("?userId=1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("userId", Matchers.everyItem(equalTo(1)))
                .extract()
                .response()
                .prettyPrint();
    }

    @Test
    public void getResponseByZeroUserId() {
        request.when()
                .get("?userId=0")
                .then()
                .assertThat()
                .statusCode(200)
                .body("isEmpty()", Matchers.equalTo(true));
    }

    @Test
    public void getResponseByEmptyUserId() {
        request.when()
                .get("?userId=")
                .then()
                .assertThat()
                .statusCode(200)
                .body("isEmpty()", Matchers.equalTo(true));
    }

    @Test
    public void getResponseByIncorrectUserId() {
        request.when()
                .get("?userId=!@#$%^&()")
                .then()
                .assertThat()
                .statusCode(200)
                .body("isEmpty()", Matchers.equalTo(true));
    }

    @Test
    public void getResponseByCorrectPostId() {
        request.when()
                .get("1")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .extract()
                .response()
                .prettyPrint();
    }

    @Test
    public void getResponseByZeroPostId() {
        request.when()
                .get("0")
                .then()
                .assertThat()
                .statusCode(200)
                .body("isEmpty()", Matchers.equalTo(true));
    }

    @Test
    public void getResponseByIncorrectPostId() {
        request.when()
                .get("!@#$%^&*()")
                .then()
                .assertThat()
                .statusCode(404)
                .body("isEmpty()", Matchers.equalTo(true));
    }

    @Test
    public void getResponseByAllResources() {
        request.when()
                .get("")
                .then()
                .assertThat()
                .statusCode(200)
                .body("", Matchers.hasSize(100));
    }
}