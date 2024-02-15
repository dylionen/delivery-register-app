package pl.app.delregapp.models.DTO.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Result {
    @JsonProperty("address_components")
    private List<AddressComponent> addressComponents;
    @JsonProperty("formatted_address")
    private String formattedAddress;
    private Geometry geometry;
    @JsonProperty("place_id")
    private String placeId;
    @JsonProperty("plus_code")
    private PlusCode plusCode;
    private List<String> types;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
