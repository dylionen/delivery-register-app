package pl.app.delregapp.models.DB;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SortNatural;
import pl.app.delregapp.modules.accounts.models.DB.User;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Localization localization;

    @ManyToOne
    private User insertUser;

    @ManyToOne
    private User deliverUser;

    private LocalDateTime inserted;
    private LocalDateTime assigned;
    private LocalDateTime delivered;

    private Double locX;
    private Double locY;

    private String clientName;
    private String formattedAddress;

    private Double distance;
    private String distanceStr;

    private Double price;
    private Boolean isActive;

}
