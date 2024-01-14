package com.nordea.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestCountryDTO {
    private String name;
    @JsonProperty("country_code")
    private String cca2;
}
