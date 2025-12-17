package dev.anil.flightticketbookingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class SignInRequestDTO {
    private String username;
    private String password;
}
