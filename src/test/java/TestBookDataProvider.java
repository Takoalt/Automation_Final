import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Lombok;
import org.example.LombokBuilder;
import org.example.LombokData;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestBookDataProvider {

    @org.testng.annotations.DataProvider(name = "bookISBN")
    public Object[][] createBookData() {
        return new Object[][]{
                {"9781449331818"},
                {"9781449337711"},
                {"9781449365035"},
                {"9781491904244"}
        };
    }

    @Test(dataProvider = "bookISBN")
    public void testGetRequestDataProvider(String bookISBN) {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/BookStore/v1";

        Response response = RestAssured
                .given()
                .queryParam("ISBN", bookISBN)
                .when()
                .get("/Book")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        String responseISBN = response.jsonPath().getString("isbn");

        Assert.assertEquals(responseISBN, bookISBN, "The returned ISBN should match the requested ISBN");

    }

    @Test
    public void createUser() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/Account";

        LombokData userData = new LombokData();
        userData.setUsername("takoalt");
        userData.setPassword("Takotakoo1!");
        userData.setUserID("27");

        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/Account/v1/User")
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code is 201");

        LombokData createdUser = response.as(LombokData.class);

        String userId = response.jsonPath().getString("userID");
        Assert.assertNotNull(userId, "userID should not be null");

    }

    @Test
    public void createBook() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/Account";
        LombokData userData = new LombokData();
        userData.setUsername("takoalt");
        userData.setPassword("tako");
        userData.setUserID("27");

        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/Account/v1/User")
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 400, "Expected status code is 400");

        String errorMessage = response.jsonPath().getString("message");
        String expectedMessage = "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), " +
                "one uppercase ('A'-'Z'), one lowercase ('a'-'z'), " +
                "one special character and Password must be eight characters or longer.";
    }
}
