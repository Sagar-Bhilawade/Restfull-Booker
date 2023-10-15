package com.app.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import com.app.endpoint.APIConstants;
import com.app.payload.AuthDetails;
import com.app.base.BaseTest;
import org.hamcrest.Matchers;
import org.testng.ITestContext;
import org.testng.annotations.Test;


public class IntegrationTest extends BaseTest {
    String token;
    @Test
    @Owner("Sagar | nowhere")
    @Description("Create Booking")
    public void  testCreateBooking(ITestContext iTestContext) throws JsonProcessingException {
        requestSpecification.basePath(APIConstants.CREATE_AND_UPDATE_BOOKING_URL);
        response= RestAssured.given().spec(requestSpecification).when().body(payLoadManager.createPayload()).post();
        validatableResponse =response.then().log().all();
        jsonPath = JsonPath.from(response.asString());
        String bookingId= jsonPath.getString("bookingid");
        System.out.println("Booking Id::"+bookingId);
        validatableResponse.statusCode(200);
        iTestContext.setAttribute("bookingid", jsonPath.getString("bookingid"));




    }
    @Test(groups = "integration", dependsOnMethods = "testCreateBooking")
    public void testCreateAndUpdateBooking(ITestContext iTestContext) throws JsonProcessingException {
        token = getToken();
        String bookingId = (String) iTestContext.getAttribute("bookingid");
        System.out.println("Shagie - " + bookingId);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.basePath(APIConstants.UPDATE_BOOKING + "/" + bookingId);
        Response response = RestAssured.given().spec(requestSpecification).cookie("token",token)
                .when().body(payLoadManager.updatedPayload()).put();
        ValidatableResponse validatableResponse = response.then().log().all();
        validatableResponse.body("firstname", Matchers.is("Shagie"));

    }

    @Test(groups = "integration",dependsOnMethods = "testCreateAndUpdateBooking")
    public void testDeleteCreatedBooking(ITestContext iTestContext) {
        requestSpecification.basePath(APIConstants.UPDATE_BOOKING + "/" + (String) iTestContext.getAttribute("bookingid")).cookie("token",token);
        ValidatableResponse validatableResponse = RestAssured.given().spec(requestSpecification).auth().basic("admin", "password123")
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);
    }

}
