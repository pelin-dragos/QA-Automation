package com.qa.automation.project16.api;

import com.qa.automation.project16.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * API client for JSONPlaceholder /posts endpoint.
 * <p>
 * Uses RestAssured with base URL from config. No hardcoded hosts or secrets.
 * JSONPlaceholder does not persist POST/PUT/DELETE; responses may still return 201/200.
 */
public final class PostsApiClient {

    private static RequestSpecification baseSpec() {
        return RestAssured.given()
                .baseUri(TestConfig.getApiBaseUrl())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

    /**
     * POST a new post. Body: title, body, userId. Returns response for assertion (status, id, title).
     */
    public static Response createPost(String title, String body, int userId) {
        return baseSpec()
                .body(Map.of("title", title != null ? title : "", "body", body != null ? body : "", "userId", userId))
                .when()
                .post("posts");
    }

    /**
     * GET a single post by id. Returns response for assertion.
     */
    public static Response getPost(int id) {
        return baseSpec()
                .when()
                .get("posts/{id}", id);
    }

    /**
     * GET all posts. Returns response for assertion (e.g. list size).
     */
    public static Response getAllPosts() {
        return baseSpec()
                .when()
                .get("posts");
    }

    /**
     * PUT update a post by id. Body: id, title, body, userId. Returns response.
     */
    public static Response updatePost(int id, String title, String body, int userId) {
        return baseSpec()
                .body(Map.of("id", id, "title", title != null ? title : "", "body", body != null ? body : "", "userId", userId))
                .when()
                .put("posts/{id}", id);
    }

    /**
     * DELETE a post by id. Returns response (JSONPlaceholder may return 200 without persisting).
     */
    public static Response deletePost(int id) {
        return baseSpec()
                .when()
                .delete("posts/{id}", id);
    }

    private PostsApiClient() {}
}
