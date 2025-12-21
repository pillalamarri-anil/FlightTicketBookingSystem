package dev.anil.flightticketbookingsystem.OAuth2;

import dev.anil.flightticketbookingsystem.models.UserModels.User;
import dev.anil.flightticketbookingsystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // for this application, email is the unique identifier of the user, not userid

        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            UserDetails userDetails = new dev.anil.selfauthentication.OAuth2.CustomUserDetails(user.get());
            return userDetails;

        }
        throw new UsernameNotFoundException(email);
    }
}