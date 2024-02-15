package pl.app.delregapp.models.DTO.Map;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class PlusCode {
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

}
