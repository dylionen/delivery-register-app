package pl.app.delregapp.models.DTO.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Geometry {
    private Location location;
    @JsonProperty("location_type")
    private String locationType;
    private Viewport viewport;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
