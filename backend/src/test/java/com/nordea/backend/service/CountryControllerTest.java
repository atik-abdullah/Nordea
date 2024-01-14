package com.nordea.backend.service;

import com.nordea.backend.controller.CountryController;
import com.nordea.backend.domain.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CountryController.class)
public class CountryControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private CountryService countryService;
    static String COUNTRIES_INFO_URL = "/v1/countries";
    static String COUNTRIES_INFO_URL_WITH_COUNTRY_NAME = "/v1/countries/Finland";

    @Test
    public void retrieveAllCountries() throws Exception {
        //call GET "/countries"  application/json

        List<Country> countries = new ArrayList<>();

        countries.add(new Country("Finland", "FI", "Helsinki", 5491817, "http://"));
        countries.add(new Country("England", "EN", "London", 5491818, "http://"));
        countries.add(new Country("Sweden", "SW", "Stockholm", 5491819, "http://"));

        when(countryService.findAll()).thenReturn(Flux.fromIterable(countries));

        webTestClient
                .get()
                .uri(COUNTRIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Country.class)
                .hasSize(3);
    }

    @Test
    public void retrieveCountry() throws Exception {
        //call GET "/countries"  application/json

        Country country = new Country("Finland", "FI", "Helsinki", 5491817, "http://");

        when(countryService.findOne("Finland")).thenReturn(Mono.just(country));

        webTestClient
                .get()
                .uri(COUNTRIES_INFO_URL_WITH_COUNTRY_NAME)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Country.class)
                .hasSize(1);
    }
}