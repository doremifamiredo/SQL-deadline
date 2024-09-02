package ru.netology.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

    public static void getCards() {
        Response response = given()
                .spec(requestSpec)
                .when().log().all()
                .get("/api/cards")
                .then().log().all()
                .extract().response();

    }
}
