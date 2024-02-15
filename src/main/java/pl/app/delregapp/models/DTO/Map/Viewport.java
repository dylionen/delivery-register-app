package pl.app.delregapp.models.DTO.Map;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Viewport {
    private Northeast northeast;
    private Southwest southwest;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();
}
