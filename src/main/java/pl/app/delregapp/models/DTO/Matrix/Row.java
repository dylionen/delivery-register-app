package pl.app.delregapp.models.DTO.Matrix;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Row {
    private List<Element> elements;
}
