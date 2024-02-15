package pl.app.delregapp.modules.accounts.services;

import pl.app.delregapp.models.DTO.PanelDTO;
import pl.app.delregapp.modules.accounts.models.DB.User;
import pl.app.delregapp.modules.accounts.models.DTO.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);

    User findUserByEmail(String email);

    List<UserDTO> findAllUsers();

    User findUserByLogin(String name);

    void saveConfiguration(PanelDTO panel);

    User getMyUser();

    List<User> getAllDelivers();

    User getUserById(Long id);
}