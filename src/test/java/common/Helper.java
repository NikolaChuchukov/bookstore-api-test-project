package common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static common.EncryptionUtils.decode;
import static common.RequestBodies.createUser;

public class Helper {

    public static String token;
    public void generateToken() {
        String encryptedPass = ConfigUtils.getProperty("encrypted_password");
        RestAssured.baseURI = ConfigUtils.getProperty("url");
        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(createUser.replace("PARAM1", ConfigUtils.getProperty("username"))
                        .replace("PARAM2", decode(encryptedPass)));

        Response response = request.post(ConfigUtils.getProperty("generate_token_endpoint"));

        if (response.getStatusCode() == 200) {
            token = "Bearer " + response.jsonPath().getString("token");
            System.out.println("Token generated: " + token);
        } else {
            System.out.println("Failed to generate token, status code: " + response.getStatusCode());
        }
    }

    public static String getToken(String username, String password) {
        RestAssured.baseURI = ConfigUtils.getProperty("url");
        RequestSpecification request = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(createUser.replace("PARAM1", username)
                        .replace("PARAM2", password));

        Response response = request.post(ConfigUtils.getProperty("generate_token_endpoint"));

        if (response.getStatusCode() == 200) {
            token = "Bearer " + response.jsonPath().getString("token");
            System.out.println("Token generated: " + token);
            return token;
        } else {
            System.out.println("Failed to generate token, status code: " + response.getStatusCode());
            return null;
        }
    }

}
