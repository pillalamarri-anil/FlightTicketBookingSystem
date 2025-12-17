package dev.anil.flightticketbookingsystem.DTOs;


@lombok.Getter
@lombok.Setter


public class SignUpRequestDTO {
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
}