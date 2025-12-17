package dev.anil.flightticketbookingsystem.DTOs;

import dev.anil.flightticketbookingsystem.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private String username;
    private String email;

    public static UserDTO from(User user) {
        if(user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getName());

        return userDTO;
    }
}