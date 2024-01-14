package com.nordea.backend.service;

import com.nordea.backend.domain.Country;
import com.nordea.backend.exception.CountryNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
@Component
public class CountryService {
    private static List<Country> countries = new ArrayList<>();

    static {
        countries.add(new Country("Finland","FI", "Helsinki", 5491817, "http://"));
        countries.add(new Country("England", "EN", "London", 5491818, "http://"));
        countries.add(new Country("Sweden", "SW", "Stockholm", 5491819, "http://"));
    }
    public Flux<Country> findAll() {
        return Flux.fromIterable(countries);
    }
    public Mono<Country> findOne(String name) {
        Predicate<? super Country> predicate = country -> country.getName().equals(name);

        Country country;
        if (countries.stream().filter(predicate).findFirst().isPresent()){
            country = countries.stream().filter(predicate).findFirst().get();
        } else {
            throw new CountryNotFoundException("Country with name: " + name +" is not found");
        }
        return Mono.just(country);
    }
}
