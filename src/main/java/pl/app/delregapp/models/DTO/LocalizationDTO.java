package pl.app.delregapp.models.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LocalizationDTO {

    private Long id;
    private String name;
    private String address;
    private String flatNumber;
    private String houseNumber;
    private String zipCode;
    private String city;

    private Double posX;
    private Double posY;

    private Boolean isActive;
}
