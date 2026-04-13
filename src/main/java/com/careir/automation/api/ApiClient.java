package com.careir.automation.api;

import com.careir.automation.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Central RestAssured configuration for future API checks (tokens, preconditions, oracles).
 */
public final class ApiClient {

    private static final Logger LOG = LogManager.getLogger(ApiClient.class);

    private ApiClient() {
    }

    public static boolean isEnabled() {
        return ConfigReader.getBoolean("api.enabled", false);
    }

    public static RequestSpecification baseRequest() {
        if (!isEnabled()) {
            throw new IllegalStateException("ApiClient disabled (api.enabled=false)");
        }
        String baseUri = ConfigReader.get("api.base.uri");
        RestAssured.baseURI = baseUri;
        RequestSpecification spec = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
        String token = ConfigReader.get("api.bearer.token", "");
        if (!token.isBlank()) {
            spec = spec.header("Authorization", "Bearer " + token);
        }
        LOG.debug("API base URI: {}", baseUri);
        return spec;
    }

    public static Response get(String path) {
        return baseRequest().when().get(path).then().extract().response();
    }
}
