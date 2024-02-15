package pl.app.delregapp.modules.accounts.models.DTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Nazwa nie może być pusta")
    private String name;

    @NotEmpty(message = "Login nie może być pusty")
    private String login;

    @NotEmpty(message = "Email nie może być pusty")
    @Email
    private String email;

    @NotEmpty(message = "Hasło nie możde być puste")
    private String password;

    private String repeatPassword;
    private List<String> roles;
    private Boolean active;
}