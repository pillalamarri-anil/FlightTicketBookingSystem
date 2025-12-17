package dev.anil.flightticketbookingsystem.DTOs;

import dev.anil.flightticketbookingsystem.models.Booking;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class BookingResponseDTO {

    Booking booking;
    ResponseStatus status;
}
