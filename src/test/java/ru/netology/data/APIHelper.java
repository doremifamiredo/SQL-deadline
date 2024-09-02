package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.*;

import static io.restassured.RestAssured.given;

public class APIHelper {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private APIHelper(){}

    public static void login(DataHelper.AuthInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when().log().all()
                .post("/api/auth")
                .then().log().all()
                .statusCode(200);
    }

    public static String getToken(DataHelper.VerifiC code) {
        String token = given()
                .spec(requestSpec)
                .body(code)
                .when().log().all()
                .post("/api/auth/verification")
                .then().log().all()
                .extract().path("token");
        return token;
    }

    public static DataHelper.Card[] getCards(String token) {
        DataHelper.Card[] cardsInfo = given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when().log().all()
                .get("/api/cards")
                .then().log().all()
                .extract().body().as(DataHelper.Card[].class);
        return cardsInfo;
    }

    public static void transfer(DataHelper.Transfer transIfo, String token) {
        given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(transIfo)
                .when().log().all()
                .post("/api/transfer")
                .then().log().all()
                .statusCode(200);
    }
}
