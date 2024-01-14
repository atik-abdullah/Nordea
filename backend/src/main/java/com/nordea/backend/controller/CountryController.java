package com.nordea.backend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nordea.backend.domain.Country;
import com.nordea.backend.dto.CountryDTO;
import com.nordea.backend.dto.RestCountryByNameDTO;
import com.nordea.backend.dto.RestCountryDTO;
import com.nordea.backend.service.CountryService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class CountryController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private CountryService service;
    public CountryController (CountryService service) {
        this.service = service;
    }

    @GetMapping("/countries")
    public Flux<CountryDTO> retrieveAllCountries(){
        ObjectMapper mapper = new ObjectMapper();
        Flux<Country> object = service.findAll();

        return service.findAll().map(country -> mapper.convertValue(country, JsonNode.class)).map(country ->
                {
                    return new CountryDTO(extractValueString(country, "name", null),
                            extractValueString(country, "country_code", null));
                });
    }
    @GetMapping("/countries/{name}")
    public Mono<Country> retrieveCountry(@PathVariable String name) {
        return service.findOne(name);
    }
    @GetMapping("/countries-from-restcountries")
    public Flux<RestCountryDTO> retrieveAllCountriesFromRestCountries() throws JSONException, IOException{

        String url = "https://restcountries.com/v3.1/all";

        Object[] object = restTemplate.getForObject(url, Object[].class);
        ObjectMapper mapper = new ObjectMapper();

        List<RestCountryDTO> listOfCountry =  Arrays.stream(object).map(country -> mapper.convertValue(country, JsonNode.class)).map(country -> {
            return new RestCountryDTO(
                    extractValueString(country, "name", "common"),
                    extractValueString(country, "cca2", null));
        }).toList();
        return Flux.fromIterable(listOfCountry);
    }

    /* If you are using WebClient to fetch data from 3rd party API and after that performing mapping on the received data
     the block() method prevents from the return type to be Flux/Mono from this API endopoint.
     Find a better solution for handling it. For now use the RestClient (see next method below)and set the return type as Flux that way
     on the client side the data will be able to handle it asynchronously (see below)*/
/*
    @GetMapping("/countries-from-restcountries/{name}")
    public Object retrieveCountryFromRestCountries(@PathVariable String name) throws JSONException, IOException, Exception {

        //The country name can contain space e.g. United Kingdom. URL can't contain spaces, therefore the string need to be encoded.
        String encodedName = UriUtils.encodePathSegment(name, "UTF-8");

        String url = "https://restcountries.com/v3.1/name/" + encodedName + "?fullText=true";

        URI uri = UriComponentsBuilder.fromUriString(url.toString()).build(true).toUri();

        Object[] object = webClientBuilder.build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Object[].class)
                .block();
        ObjectMapper mapper = new ObjectMapper();

        List<RestCountryByNameDTO> listOfCountry =  Arrays.stream(object).map(country -> mapper.convertValue(country, JsonNode.class)).map(country -> {
            return new RestCountryByNameDTO(
                    extractValueString(country, "name", "common"),
                    extractValueString(country, "cca2", null),
                    extractValueFromArray(country, "capital"),
                    extractValueInt(country, "population"));
        }).toList();

        return listOfCountry;
        //return Flux.fromIterable(listOfCountry);
    }*/

    /* Exact same approach as the upper code except the data is now fetched using RestTemplate. This approach doesn't
    prevent the endpoint to set the return type as Flux*/

    @GetMapping("/countries-from-restcountries/{name}")
    public Flux<RestCountryByNameDTO> retrieveCountryFromRestCountries(@PathVariable String name) throws JSONException, IOException, Exception {

        //The country name can contain space e.g. United Kingdom. URL can't contain spaces, therefore the string need to be encoded.
        String encodedName = UriUtils.encodePathSegment(name, "UTF-8");

        String url = "https://restcountries.com/v3.1/name/" + encodedName + "?fullText=true";

        /* Since I have already encoded part of the URL, I need to explicitly tell it to not encode again,
        Without this line, if you pass single value country name as teh path variable e.g. China everything will work
        as if there was nothing wrong, but if you pass country name where it has spaces e.g. United Sates, the
        getForObject will throw exception. Because the name was already encoded once. */

        URI uri = UriComponentsBuilder.fromUriString(url.toString()).build(true).toUri();

        Object[] object = restTemplate.getForObject(uri, Object[].class);
        ObjectMapper mapper = new ObjectMapper();

        List<RestCountryByNameDTO> listOfCountry = Arrays.stream(object).map(country -> mapper.convertValue(country, JsonNode.class)).map(country -> {
            return new RestCountryByNameDTO(
                    extractValueString(country, "name", "common"),
                    extractValueString(country, "cca2", null),
                    extractValueFromArray(country, "capital"),
                    extractValueInt(country, "population"));
        }).toList();
        return Flux.fromIterable(listOfCountry);
    }

    private static Integer extractValueInt(JsonNode country, String nodeName) {
        return country.has(nodeName) ? country.get(nodeName).asInt(): null;
    }
    private static String extractValueString(JsonNode country, String nodeName, String subNodeName) {
        if (subNodeName == null) {
            return country.has(nodeName) ? country.get(nodeName).asText() : null;
        } else {
            return country.has(nodeName) ? country.get(nodeName).get(subNodeName).asText() : null;
        }
    }

    private static Map<String, Object> convertNodeToMap(JsonNode country, String node) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(country.get(node), Map.class);
        return map;
    }

    private static String extractValueFromArray(JsonNode country, String nodeName) {
        return country.has(nodeName) ? country.get(nodeName).get(0).asText() : null;
    }
}
