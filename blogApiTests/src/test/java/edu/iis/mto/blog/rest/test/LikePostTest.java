package edu.iis.mto.blog.rest.test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class LikePostTest extends FunctionalTests {

    public static final String CONFIRMED_USER_NOT_OWNER_OF_POST = "/blog/user/3/like/1";
    public static final String NEW_USER_NOT_OWNER_OF_POST = "/blog/user/2/like/1";
    public static final String CONFIRMED_USER_OWNER_OF_POST = "/blog/user/1/like/1";

    @Test
    public void likingPostByConfirmedUserReturnsOK() {
        JSONObject jsonObj = new JSONObject();
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_OK).when()
                .post(CONFIRMED_USER_NOT_OWNER_OF_POST);
    }

    @Test
    public void likingPostByNewUserReturnsBadRequest() {
        JSONObject jsonObj = new JSONObject();
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_BAD_REQUEST).when()
                .post(NEW_USER_NOT_OWNER_OF_POST);
    }

    @Test
    public void likingPostByPostOwnerReturnsBadRequest() {
        JSONObject jsonObj = new JSONObject();
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_BAD_REQUEST).when()
                .post(CONFIRMED_USER_OWNER_OF_POST);
    }

    @Test
    public void likingPostTwiceDoesNotChangeLikesCount() {
        JSONObject jsonObj = new JSONObject();

        String urlGetFirstPost = "/blog/user/1/post";
        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().body(containsString("\"likesCount\":1")).when()
                .get(urlGetFirstPost);

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().statusCode(HttpStatus.SC_OK).when()
                .post(CONFIRMED_USER_NOT_OWNER_OF_POST);

        RestAssured.given().accept(ContentType.JSON).header("Content-Type", "application/json;charset=UTF-8")
                .body(jsonObj.toString()).expect().log().all().body(containsString("\"likesCount\":1")).when()
                .get(urlGetFirstPost);
    }

}
