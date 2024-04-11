package com.darfik.skycast.location;

import com.darfik.skycast.commons.models.ResponseJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class LocationJson extends ResponseJson {
    @JsonProperty("name")
    private String locationName;

    @JsonProperty("lat")
    private BigDecimal latitude;

    @JsonProperty("lon")
    private BigDecimal longitude;
}
