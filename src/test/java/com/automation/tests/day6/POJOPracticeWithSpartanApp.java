package com.automation.tests.day6;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOPracticeWithSpartanApp {

    @BeforeAll
    public static void beforeALL(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }

    public void addSpartanTest(){
        /**
         * {
         *  "gender": "Female",
         *   "name": "Nanino",
         *   "phone": "1234567890"
         *   }
         */

        Map<String, String> spartan = new HashMap<>();
        spartan.put("gender", "Male");
        spartan.put("name", "Nanino");
        spartan.put("phone", "1245465343");

        RequestSpecification requestSpecification = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan);

        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan).
                when().
                post("/spartans").prettyPeek();


        response.then().statusCode(201);
        response.then().body("success", is("A Spartan is Born!"));
        //deserialization
        Spartan spartanResponse = response.jsonPath().getObject("data", Spartan.class);
        Map<String, Object> spartanResponseMap = response.jsonPath().getObject("data", Map.class);

        System.out.println(spartanResponse);
        System.out.println(spartanResponseMap);

        //spartanResponse is a Spartan
        System.out.println(spartanResponse instanceof Spartan);// must be true

    }


}
