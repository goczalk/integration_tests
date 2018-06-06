package edu.iis.mto.blog.rest.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

public class SearchPostTest extends FunctionalTests {

    @Test
    public void searchingPostShouldReturnAppriopriateLikeCount() {
        JSONObject jsonObj = new JSONObject();

        String urlGetFirstPost = "/blog/user/1/post";
        String confirmedUserNotOwner = "/blog/user/3/like/2";

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().body(containsString("\"likesCount\":0")).when()
                .get(urlGetFirstPost);

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_OK).when()
                .post(confirmedUserNotOwner);

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().body(containsString("\"likesCount\":1")).when()
                .get(urlGetFirstPost);
    }

    @Test
    public void searchingPostsOfRemovedUserShouldReturnBadRequest() {
        JSONObject jsonObj = new JSONObject();
        String removedUser = "/blog/user/4/post";
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_BAD_REQUEST).when().get(removedUser);

    }
}
