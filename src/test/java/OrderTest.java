import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.LombokData;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OrderTest {

    @Test
    public void createOrder() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        LombokData userData = new LombokData();

        userData.setID(7);
        userData.setPetId(8);
        userData.setQuantity(2);
        userData.setShipDate("2025-02-05T11:10:43.281Z");
        userData.setStatus("placed");
        userData.setComplete(true);

        Response response = given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
        LombokData createdOrder = response.as(LombokData.class);

        Assert.assertEquals(createdOrder.getID(), userData.getID(), "ID does not match");
        Assert.assertEquals(createdOrder.getPetId(), userData.getPetId(), "Pet ID does not match");
        Assert.assertEquals(createdOrder.getQuantity(), userData.getQuantity(), "Quantity does not match");
        Assert.assertEquals(createdOrder.getStatus(), userData.getStatus(), "Status match");

    }

    @Test
    public void getOrderId() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        int orderId = 7;

        Response response = given()
                .header("accept", "application/json")
                .when()
                .get("/store/order/" + orderId)
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
        int returnedId = response.path("id");
        Assert.assertEquals(returnedId, orderId, "ID does not match");
    }

    @Test
    public void deleteOrder() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        int orderId = 7;

        Response response = given()
                .header("accept", "application/json")
                .when()
                .delete("/store/order/" + orderId)
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
    }

    @Test
    public void deleteOrderAgain() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        int orderId = 7;

        Response response = given()
                .header("accept", "application/json")
                .when()
                .delete("/store/order/" + orderId)
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code is 404");

        String errorMessage = response.jsonPath().getString("message");
        String expectedMessage = "Order not found";
    }

    @Test
    public void getOrderIDAgain() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        int orderId = 7;

        Response response = given()
                .header("accept", "application/json")
                .when()
                .get("/store/order/" + orderId)
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code is 404");

        String errorMessage = response.jsonPath().getString("message");
        String expectedMessage = "Order not found";
    }

    @Test
    public void createUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        LombokData userData = new LombokData();

        userData.setID(10);
        userData.setUsername("takoalt");
        userData.setFirstName("tako");
        userData.setLastName("altunashvili");
        userData.setEmail("tako@gmail.com");
        userData.setPassword("tako123#");
        userData.setPhone(593151515);
        userData.setUserStatus(1);


        Response response = given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
    }

    @Test
    public void getUserDetails() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String username = "takoalt";

        Response response = given()
                .header("accept", "application/json")
                .when()
                .get("/user/" + "username")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        String returnedUsername = response.path("username");
        String returnedFirstName = response.path("firstName");
        String returnedLastName = response.path("lastName");
        String returnedEmail = response.path("email");
        String returnedPassword = response.path("password");
        int returnedPhone = response.path("phone");
        int returnedUserStatus = response.path("userStatus");

        Assert.assertEquals(returnedUsername, "takoalt", "Username does not match");
        Assert.assertEquals(returnedFirstName, "tako", "First name does not match");
        Assert.assertEquals(returnedLastName, "altunashvili", "Last name does not match");
        Assert.assertEquals(returnedEmail, "tako@gmail.com", "Email does not match");
        Assert.assertEquals(returnedPassword, "tako123#", "Password does not match");
        Assert.assertEquals(returnedPhone, 593151515, "Phone does not match");
        Assert.assertEquals(returnedUserStatus, 1, "User status does not match");
    }


    @Test
    public void updateUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        LombokData userData = new LombokData();
        String username = "takoalt";

        userData.setID(10);
        userData.setUsername("takoalt");
        userData.setFirstName("eka");
        userData.setLastName("altunashvili");
        userData.setEmail("tako@gmail.com");
        userData.setPassword("tako123#");
        userData.setPhone(593151515);
        userData.setUserStatus(1);


        Response response = given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
    }

    @Test
    public void getUpdatedFirstName() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String username = "takoalt";

        Response response = given()
                .header("accept", "application/json")
                .when()
                .get("/store/order/" + username)
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");
        String returnedFirstName = response.path("firstName");
        Assert.assertEquals(returnedFirstName, "eka" , "ID does not match");
    }
    @Test
    public void loginUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String username = "takoalt";
        String password = "tako123#";

        Response response = given()
                .header("accept", "application/json")
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        String message = response.path("message");

        Assert.assertNotNull(message, "Response message is null");
    }
    @Test
    public void logoutUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        Response response = given()
                .header("accept", "application/json")
                .when()
                .get("/user/logout")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        String message = response.path("message");

        Assert.assertEquals(message, "ok", "Message is not 'ok'");
    }

    @Test
    public void deleteUser() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        String username = "takoalt";

        Response response = given()
                .header("accept", "application/json")
                .when()
                .delete("/user/" + username)
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200");

        String message = response.path("message");

        Assert.assertEquals(message, "takoalt", "Message does not match the username");
    }

    @Test
    public void OrderWithInvalidId() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        LombokData userData = new LombokData();
        userData.setID(3);
        userData.setPetId(8);
        userData.setQuantity(2);
        userData.setShipDate("2025-02-05T11:10:43.281Z");
        userData.setStatus("placed");
        userData.setComplete(true);

        Response response = given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 500, "Expected status code is 500");
        String errorMessage = response.path("message");
        Assert.assertEquals(errorMessage, "something bad happened", "Error message does not match");
    }

    @Test
    public void OrderWithInvalidPetId() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        LombokData userData = new LombokData();
        userData.setID(7);
        userData.setPetId(0);
        userData.setQuantity(2);
        userData.setShipDate("2025-02-05T11:10:43.281Z");
        userData.setStatus("placed");
        userData.setComplete(true);

        Response response = given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 500, "Expected status code is 500");

        String errorMessage = response.path("message");
        Assert.assertEquals(errorMessage, "something bad happened", "Error message does not match");
    }

    @Test
    public void OrderWithInvalidQuantity() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        LombokData userData = new LombokData();
        userData.setID(7);
        userData.setPetId(8);
        userData.setInvalidQuantity("test");
        userData.setShipDate("2025-02-05T11:10:43.281Z");
        userData.setStatus("placed");
        userData.setComplete(true);

        Response response = given()
                .header("accept", "application/json")
                .contentType(ContentType.JSON)
                .body(userData)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .extract()
                .response();

        Assert.assertEquals(response.getStatusCode(), 500, "Expected status code is 500");

        String errorMessage = response.path("message");
        Assert.assertEquals(errorMessage, "something bad happened", "Error message does not match");
    }

}