package dev.anil.flightticketbookingsystem.InventoryProvider;


import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

public class AmadeusAuth {

    @Value("${amedeus.api.key}")
    private String AMEDEUS_API_KEY;

    @Value("${amedeus.api.secret}")
    private String AMEDEUS_API_SECRET;


    private String token;
    private Instant expiry;

    public String getAccessToken() throws Exception {

        if (token != null && Instant.now().isBefore(expiry)) {
            return token;
        }

        String body = "grant_type=client_credentials"
                + "&client_id=" + AMEDEUS_API_KEY
                + "&client_secret=" + AMEDEUS_API_SECRET;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://test.api.amadeus.com/v1/security/oauth2/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response =
                HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(response.body());

        token = json.get("access_token").asText();
        expiry = Instant.now().plusSeconds(json.get("expires_in").asLong() - 60);

        return token;
    }
}
