package com.nordea.backend.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "name", "countryCode", "capital", "population", "flagFileUrl" })
public class Country {
    private String name;
    @JsonProperty("country_code")
    private String countryCode;
    private String capital;
    private Integer population;
    @JsonProperty("flag_file_url")
    private String flagFileUrl;

}
