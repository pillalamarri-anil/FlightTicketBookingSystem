package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.InvalidCredentialsException;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidTokenException;
import dev.anil.flightticketbookingsystem.Exceptions.UserAlreadyExistsExeption;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.models.UserModels.User;
import io.jsonwebtoken.Claims;

public interface UserService {

    User signup(String userName, String password, String email, String phoneNumber)
    throws UserAlreadyExistsExeption;
    User getUserById(long userId) throws UserNotFoundException;
    String signIn(String email, String password) throws InvalidCredentialsException;

    Claims validate(String token) throws InvalidTokenException;

    void logout(String email);

}
