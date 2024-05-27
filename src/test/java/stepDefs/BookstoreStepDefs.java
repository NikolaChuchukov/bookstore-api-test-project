package stepDefs;

import common.ConfigUtils;
import common.EncryptionUtils;
import common.Helper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.asserts.SoftAssert;


import java.util.Map;

import static common.RequestBodies.*;

public class BookstoreStepDefs {

    public Response response;
    SoftAssert softAssert = new SoftAssert();
    public String tokenForUser;
    String decryptedPassword = EncryptionUtils.decode(ConfigUtils.getProperty("encrypted_password"));
    String username = ConfigUtils.getProperty("username");
    public String contentType = "Content-Type";
    public String responseType = "application/json";
    public String authorizationHeader = "Authorization";
    public String uri = ConfigUtils.getProperty("url");


    @Given("A token is generated")
    public void generateToken() {
        tokenForUser = Helper.getToken(username,decryptedPassword);
    }

    @Given("User sends a GET request to {string}")
    public void send_request(String endpoint) {
        response = RestAssured.get(ConfigUtils.getProperty("url") + endpoint);
    }

    @Given("I send a POST request to {string}")
    public void sendPostRequest(String endpoint) {
        response = RestAssured.get(ConfigUtils.getProperty("url") + endpoint);
    }

    @Then("the response status code should be {int}")
    public void responseCodeCheck(Integer statusCode) {
        softAssert.assertTrue(response.getStatusCode() == statusCode,
                "The status code is " + response.getStatusCode());
        softAssert.assertAll();
    }

    @Then("the response should contain a list of books")
    public void listOfBooks() {
        softAssert.assertTrue(!response.getBody().jsonPath().getList("books").isEmpty());
        softAssert.assertAll();
    }

    @When("the user adds a new book with ISBN {string} and title {string}")
    public void addNewBook(String isbn, String title) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given()
                .header(authorizationHeader, tokenForUser)
                .header(contentType, responseType)
                .body(collectionOfIsbns.replace("PARAM1",ConfigUtils.getProperty("userID"))
                        .replace("PARAM2",isbn));

        response = request.post("/BookStore/v1/Books");
        System.out.println(response.prettyPrint());
    }

    @Then("the response should contain the book with ISBN {string} and title {string}")
    public void checkNewBook(String isbn, String title) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given()
                .header(authorizationHeader, tokenForUser)
                .header(contentType, responseType);

        response = request.get("/Account/v1/User/"+ConfigUtils.getProperty("userID"));
        System.out.println(response.prettyPrint());
        softAssert.assertTrue(response.getBody().asString().contains(isbn));
        softAssert.assertAll();
    }

    @Given("User gets a book with ISBN {string}")
    public void getBookRequest(String isbn) {
        response = RestAssured.get(ConfigUtils.getProperty("url") + "/BookStore/v1/Book?ISBN=" +isbn);
        System.out.println(response.prettyPrint());
    }

    @Given("User puts a book with ISBN {string} to replace {string}")
    public void putBookRequest(String newIsbn, String oldIsbn) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given()
                .header(authorizationHeader, tokenForUser)
                .header(contentType, responseType)
                .body(putIsbn.replace("PARAM1",ConfigUtils.getProperty("userID"))
                        .replace("PARAM2",newIsbn));

        response = request.put("/BookStore/v1/Books/"+oldIsbn);
    }

    @When("the user deletes the book with ISBN {string} from the collection")
    public void deleteBook(String isbn) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given()
                .header(authorizationHeader, tokenForUser)
                .header(contentType, responseType)
                .body(deleteIsbn.replace("PARAM1",ConfigUtils.getProperty("userID"))
                        .replace("PARAM2",isbn));

        response = request.delete("/BookStore/v1/Book");
    }

    @Then("the book with ISBN {string} should no longer be in the collection")
    public void theBookIsNoLongerInUserCollection(String isbn) {
        RestAssured.baseURI = uri;
        RequestSpecification request = RestAssured.given()
                .header(authorizationHeader, tokenForUser)
                .header(contentType, responseType);

        response = request.get("/Account/v1/User/"+ConfigUtils.getProperty("userID"));
        System.out.println(response.prettyPrint());
        softAssert.assertFalse(response.getBody().asString().contains(isbn));
        softAssert.assertAll();
    }

    @Then("the book details should be as follows:")
    public void checkBookDetails(DataTable dataTable) {
        Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);

        String actualIsbn = response.jsonPath().getString("isbn");
        String actualTitle = response.jsonPath().getString("title");
        String actualAuthor = response.jsonPath().getString("author");
        String actualPublisher = response.jsonPath().getString("publisher");
        String actualPages = response.jsonPath().getString("pages");

        softAssert.assertEquals(actualIsbn, expectedDetails.get("isbn"));
        softAssert.assertEquals(actualTitle, expectedDetails.get("title"));
        softAssert.assertEquals(actualAuthor, expectedDetails.get("author"));
        softAssert.assertEquals(actualPublisher, expectedDetails.get("publisher"));
        softAssert.assertEquals(actualPages, expectedDetails.get("pages"));
        softAssert.assertAll();
    }
}

