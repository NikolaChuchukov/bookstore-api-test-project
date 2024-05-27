package stepDefs;

import common.ConfigUtils;
import common.Helper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;

import static common.EncryptionUtils.decode;
import static common.RequestBodies.createUser;

public class AuthorizationStepDefs {

    private RequestSpecification request;
    private Response response;
    SoftAssert softAssert = new SoftAssert();
    public String contentType = "Content-Type";
    public String responseType = "application/json";
    public String authorizationHeader = "Authorization";
    public String uri = ConfigUtils.getProperty("url");

    @Given("a user with username {string} and password {string}")
    public void requestForUser(String username, String password) {
        RestAssured.baseURI = uri;;
        request = RestAssured.given()
                .header(contentType, responseType)
                .body(createUser.replace("PARAM1", username)
                        .replace("PARAM2", password));
    }

    @When("the user sends the request to {string}")
    public void authorizeTheUser(String url) {
        response = request.post(url);
        System.out.println(response.prettyPrint());
    }

    @Then("Response status code should be {int}")
    public void checkresponse(Integer statusCode) {
        softAssert.assertEquals(response.getStatusCode(), statusCode.intValue(),
                "The status code is " + response.getStatusCode());
        softAssert.assertAll();
    }

    @Then("the response should contain a {string}")
    public void checktokenInResponse(String jsonPath) {
        String token = response.jsonPath().getString(jsonPath);
        System.out.println(token);
        softAssert.assertNotNull(token);
        softAssert.assertAll();
    }

    @Then("Response contains {string} with value {string}")
    public void responseCheck(String jsonPath, String value) {
        String messageFromResponse = response.jsonPath().getString(jsonPath);
        softAssert.assertTrue(messageFromResponse.equalsIgnoreCase(value), "Message is wrong. It is " +
                messageFromResponse + " rather than " + value);
    }

    @Given("a user with correct username and password")
    public void correctUsernamePass() {
        RestAssured.baseURI = uri;
        String encryptedPass = ConfigUtils.getProperty("encrypted_password");
        request = RestAssured.given()
                .header(contentType, responseType)
                .body(createUser.replace("PARAM1",ConfigUtils.getProperty("username"))
                        .replace("PARAM2", decode(encryptedPass)));
    }

    @When("A request sent to {string} to delete user {string} with password {string}")
    public void deleteUser(String url, String username, String password) {
        String userId = response.jsonPath().getString("userID");
        String tokenForDeletedUser = Helper.getToken(username,password);
        RestAssured.baseURI = ConfigUtils.getProperty("url");
        RequestSpecification request = RestAssured.given()
                .header(authorizationHeader, tokenForDeletedUser)
                .header(contentType, responseType);

        response = request.delete(url + userId);
    }
}
