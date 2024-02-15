package pl.app.delregapp.modules.accounts.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.app.delregapp.modules.accounts.models.DB.Role;
import pl.app.delregapp.modules.accounts.models.DB.User;
import pl.app.delregapp.modules.accounts.repositories.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(username);

        if (user != null && user.getActive()) {
            return new org.springframework.security.core.userdetails.User(user.getName(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } else if (user != null) {
            throw new UsernameNotFoundException("Konto nieaktywne");
        } else {
            throw new UsernameNotFoundException("Nieprawidłowy login lub hasło.");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}