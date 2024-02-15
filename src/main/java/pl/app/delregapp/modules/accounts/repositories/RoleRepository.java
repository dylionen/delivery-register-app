package pl.app.delregapp.modules.accounts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.app.delregapp.modules.accounts.models.DB.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}