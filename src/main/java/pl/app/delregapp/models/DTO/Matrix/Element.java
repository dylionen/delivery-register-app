package pl.app.delregapp.models.DTO.Matrix;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Element {
    private Distance distance;
    private Duration duration;
    private String origin;
    private String destination;
    private String status;
}
