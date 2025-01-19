import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Davaleba {

    @Test
    public void getRequest() {
        Response response = RestAssured.get("https://bookstore.toolsqa.com/BookStore/v1/Books");
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().asString());
        System.out.println(response.getTime());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));

        int statusCode = response.getStatusCode();

        Assert.assertEquals(statusCode, 200, "Expected status code is 200");

    }

    @Test
    public void postExample() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/Account";
        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "test");
        requestBody.put("password", "Test1!");

        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/v1/User")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201, "Expected status code is 201");

        String userId = response.jsonPath().getString("userID");
        Assert.assertNotNull(userId, "userID should not be null");
    }

    @Test
    public void postExample2() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com/Account";
        JSONObject requestBody = new JSONObject();
        requestBody.put("userName", "test");
        requestBody.put("password", "test0");

        Response response = RestAssured
                .given()
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/v1/User")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400, "Expected status code is 400");

        String responseBody = response.getBody().asString();
        String errorMessage = response.jsonPath().getString("message");
        Assert.assertEquals(errorMessage, "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), " +
                "one uppercase ('A'-'Z'), one lowercase ('a'-'z'), " +
                "one special character and Password must be eight characters or longer.", "Error message does not match"
        );
    }
}