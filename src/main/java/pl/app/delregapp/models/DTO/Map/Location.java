package pl.app.delregapp.models.DTO.Map;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Location {
    private Double lat;
    private Double lng;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
