package pl.app.delregapp.models.DTO.Map;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GencodeResult {
    private List<Result> result;
    private String status;

}
