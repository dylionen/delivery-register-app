package pl.app.delregapp.models.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDTO {
    private LocalizationDTO localization;
    private String clientName;
    private Double price;
}
