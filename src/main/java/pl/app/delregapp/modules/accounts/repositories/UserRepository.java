package pl.app.delregapp.modules.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.app.delregapp.modules.accounts.models.DB.Role;
import pl.app.delregapp.modules.accounts.models.DB.User;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);
    User findByEmail(String email);
    User findUserByLogin(String login);

    List<User> findUserByRolesIn(Collection<Role> roles);
}