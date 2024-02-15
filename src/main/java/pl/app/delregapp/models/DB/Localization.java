package pl.app.delregapp.models.DB;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Localization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String formattedAddress;
}
