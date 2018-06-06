package edu.iis.mto.blog.rest.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class SearchUserTest extends FunctionalTests {

    @Test
    public void searchingUserShouldNotReturnInfoAboutRemovedUsers() {
        JSONObject jsonObj = new JSONObject();


        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).param("searchString", "@").expect().log().all()
                .body(not((containsString("\firstName\":\"Klaudia\"")))).when().get("/blog/user/find");
    }
}
