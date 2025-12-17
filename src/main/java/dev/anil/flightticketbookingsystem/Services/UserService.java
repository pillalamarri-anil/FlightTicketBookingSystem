package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.InvalidCredentialsException;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidDateException;
import dev.anil.flightticketbookingsystem.Exceptions.UserAlreadyExistsExeption;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.models.User;
import dev.anil.flightticketbookingsystem.repos.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public interface UserService {

    User signup(String userName, String password, String email, String phoneNumber)
    throws UserAlreadyExistsExeption;
    User getUserById(long userId) throws UserNotFoundException;
    String signIn(String email, String password) throws InvalidCredentialsException;
}
