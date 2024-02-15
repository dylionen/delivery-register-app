package pl.app.delregapp.modules.accounts.models.DB;

import jakarta.persistence.*;
import lombok.Data;
import pl.app.delregapp.models.DB.Localization;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String name;
    @Column(nullable = false)
    private Boolean active;

    @ManyToOne
    private Localization localization;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();
}