package com.mycompany.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static final String BASE_URL =
            "http://localhost:8080";

    private static final HttpClient CLIENT =
            HttpClient.newHttpClient();

    private static String token;

    private ApiClient() {
    }

    public static void setToken(String novoToken) {
        token = novoToken;
    }

    public static String getToken() {
        return token;
    }

    public static void limparToken() {
        token = null;
    }

    public static HttpResponse<String> get(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET();

        adicionarAutorizacao(builder);

        return CLIENT.send(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static HttpResponse<String> post(
            String endpoint,
            String json)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(
                        HttpRequest.BodyPublishers.ofString(json)
                );

        adicionarAutorizacao(builder);

        return CLIENT.send(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static HttpResponse<String> put(
            String endpoint,
            String json)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .PUT(
                        HttpRequest.BodyPublishers.ofString(json)
                );

        adicionarAutorizacao(builder);

        return CLIENT.send(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    public static HttpResponse<String> delete(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest.Builder builder = HttpRequest
                .newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .DELETE();

        adicionarAutorizacao(builder);

        return CLIENT.send(
                builder.build(),
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private static void adicionarAutorizacao(
            HttpRequest.Builder builder) {

        if (token != null && !token.isBlank()) {
            builder.header(
                    "Authorization",
                    "Bearer " + token
            );
        }
    }
}
