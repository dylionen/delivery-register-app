package pl.app.delregapp.modules.accounts.services;


import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.app.delregapp.configurations.StaticFields;
import pl.app.delregapp.models.DTO.PanelDTO;
import pl.app.delregapp.modules.accounts.models.DB.Role;
import pl.app.delregapp.modules.accounts.models.DB.User;
import pl.app.delregapp.modules.accounts.models.DTO.UserDTO;
import pl.app.delregapp.modules.accounts.repositories.RoleRepository;
import pl.app.delregapp.modules.accounts.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            //pierwsza rejestracja
            role = createAdminRole();
        } else {
            role = roleRepository.findByName("ROLE_USER");
            if (role == null) {
                role = createUserRole();
            }
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User findUserByLogin(String name) {
        return userRepository.findUserByLogin(name);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    private UserDTO mapToUserDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setName(user.getName());
        userDto.setLogin(user.getLogin());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(Role::getName).toList());
        userDto.setActive(user.getActive());
        return userDto;
    }

    private Role createAdminRole() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    private Role createUserRole() {
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void saveConfiguration(PanelDTO panel) {
        User user = getMyUser();
        user.setLocalization(panel.getActualLocalization());
        userRepository.save(user);
    }

    @Override
    public User getMyUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findUserByLogin(auth.getName());
    }

    @Override
    public List<User> getAllDelivers() {
        return userRepository.findUserByRolesIn(List.of(roleRepository.findByName(StaticFields.ROLE_DELIVER)));
    }

    @Override
    public User getUserById(Long id){
        return userRepository.getReferenceById(id);
    }


}