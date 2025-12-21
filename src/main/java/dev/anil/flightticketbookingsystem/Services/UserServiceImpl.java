package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.InvalidCredentialsException;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidTokenException;
import dev.anil.flightticketbookingsystem.Exceptions.UserAlreadyExistsExeption;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.models.UserModels.Token;
import dev.anil.flightticketbookingsystem.models.UserModels.User;
import dev.anil.flightticketbookingsystem.repos.TokenRepository;
import dev.anil.flightticketbookingsystem.repos.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private SecretKey secretKey;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository, SecretKey secretKey) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.secretKey = secretKey;

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

                HashMap<String, Object> claims = new HashMap<>();
                claims.put("name", user.getName());
                claims.put("iss", "scaler.com");
                claims.put("sub", user.getEmail());
                claims.put("exp", Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond());
                claims.put("iat", Instant.now().getEpochSecond());

                String jwt = Jwts.builder().claims(claims).signWith(secretKey).compact();

                Token token = new Token();
                token.setExpiryDate(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)));
                tokenRepository.save(token);

                List<Token> tokens = new ArrayList<>();
                tokens.add(token);
                user.setTokens(tokens);
                userRepository.save(user);
                return jwt;
            }
            else
                throw new InvalidCredentialsException("Invalid Credenitals");
        }
        else {
            // redirect to the login page
            return null;
        }
    }

    @Override
    public Claims validate(String token) throws InvalidTokenException {

        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = parser.parseSignedClaims(token).getPayload();

        long date = (Long)claims.get("exp");
        if(date < Instant.now().getEpochSecond())
            throw new InvalidTokenException("Invalid Token");

        return claims;
    }

    @Override
    public void logout(String email) {

    }
}
