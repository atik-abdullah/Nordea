package com.nordea.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "name", "cca2", "capital", "population"})
public class RestCountryByNameDTO {
    private String name;
    @JsonProperty("country_code")
    private String cca2;
    private String capital;
    private Integer population;
}