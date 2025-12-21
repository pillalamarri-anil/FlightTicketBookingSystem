package dev.anil.flightticketbookingsystem.Controllers;

import dev.anil.flightticketbookingsystem.DTOs.SignInRequestDTO;
import dev.anil.flightticketbookingsystem.DTOs.SignUpRequestDTO;
import dev.anil.flightticketbookingsystem.DTOs.UserDTO;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidCredentialsException;
import dev.anil.flightticketbookingsystem.Exceptions.UserAlreadyExistsExeption;
import dev.anil.flightticketbookingsystem.Services.UserService;
import dev.anil.flightticketbookingsystem.models.UserModels.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody SignUpRequestDTO requestDTO) throws UserAlreadyExistsExeption {
        User user = userService.signup(requestDTO.getUsername(), requestDTO.getPassword(), requestDTO.getEmail(), requestDTO.getPhoneNumber());
        return UserDTO.from(user);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody SignInRequestDTO requestDTO) throws InvalidCredentialsException {
        return userService.signIn(requestDTO.getEmail(), requestDTO.getPassword());
    }
}
