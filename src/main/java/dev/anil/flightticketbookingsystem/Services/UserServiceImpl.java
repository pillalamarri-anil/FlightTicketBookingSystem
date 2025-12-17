package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.InvalidCredentialsException;
import dev.anil.flightticketbookingsystem.Exceptions.UserAlreadyExistsExeption;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.models.User;
import dev.anil.flightticketbookingsystem.repos.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signup(String userName, String password, String email, String phoneNumber)
            throws UserAlreadyExistsExeption
    {

        if (userRepository.existsUserByMobile(phoneNumber))
            throw new UserAlreadyExistsExeption("User with phone number already exists");

        if(userRepository.existsByEmail(email))
            throw new UserAlreadyExistsExeption("User with email already exists");

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setName(userName);
        user.setMobile(phoneNumber);
        userRepository.save(user);
        return user;
    }

    public User getUserById(long userId) throws UserNotFoundException
    {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty())
            throw new UserNotFoundException("User not found");

        return userOptional.get();
    }

    public String signIn(String email, String password) throws InvalidCredentialsException {

        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            if(bCryptPasswordEncoder.matches(password, userOptional.get().getPassword()))
            {
                User user = userOptional.get();

                // generate token
                return RandomStringUtils.randomAlphanumeric(16);
            }
            else
                throw new InvalidCredentialsException("Invalid Credenitals");
        }
        else {
            // redirect to the login page
            return null;
        }
    }
}
