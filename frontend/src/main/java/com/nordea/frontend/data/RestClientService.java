package com.nordea.frontend.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RestClientService implements Serializable {

    VerticalLayout verticalLayout = new VerticalLayout();
    @Value("${connect.address}")
    private String CONNECTION_URL;
    /**
     * Returns non-parsed JSON response objects from the REST service.
     *
     * Useful when you don't want to create a DTO class, or the response data has a
     * dynamic structure.
     */
    public List<JsonNode> getAllCountries() {
        String fullUrl = CONNECTION_URL+"/v1/countries";

        // Fetch from 3rd party API; configure fetch
        final RequestHeadersSpec<?> spec = WebClient.create().get().uri(fullUrl);

        // Fetch and map the result
        return spec.retrieve().toEntityList(JsonNode.class).block().getBody();
    }

    public List<JsonNode> getAllCountriesFromThirdParty() {
        String fullUrl = CONNECTION_URL+"/v1/countries-from-restcountries";

        // Fetch from 3rd party API; configure fetch
        final RequestHeadersSpec<?> spec = WebClient.create().get().uri(fullUrl);

        // Fetch and map the result
        return spec.retrieve().toEntityList(JsonNode.class).block().getBody();
    }

    public List<JsonNode> getCountryByName(String name) {
        String fullUrl = CONNECTION_URL+"/v1/countries/";

        // Fetch from 3rd party API; configure fetch
        final RequestHeadersSpec<?> spec = WebClient.create().get().uri(fullUrl + name);

        // Fetch and map the result
        return spec.retrieve().toEntityList(JsonNode.class).block().getBody();
    }

    public List<JsonNode> getCountryByNameFromThirdParty(String name) {
        String fullUrl = CONNECTION_URL+"/v1/countries-from-restcountries/";

        final RequestHeadersSpec<?> spec = WebClient.create().get()
                .uri(fullUrl + name);

        Mono<Object[]> entityResponse = spec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(Object[].class);
            } else if (response.statusCode().is5xxServerError()) {
                //The following code executes only if you haven't implemented a Vaadin ErrorHandler (See CustomErrorHandler class)
                return response.createException().flatMap(error -> {
                    return Mono.error(new Exception("Printed on console: Such country don't exist"));
                });
            } else {
                return response.createException().flatMap(Mono::error);
            }
        });

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(new ArrayList<Object>(Arrays.asList(entityResponse.block())));
        return new ArrayList<>(Arrays.asList(jsonNode));
    }
}