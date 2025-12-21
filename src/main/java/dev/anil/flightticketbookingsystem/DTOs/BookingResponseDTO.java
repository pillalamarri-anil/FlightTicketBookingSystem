package dev.anil.flightticketbookingsystem.DTOs;

import dev.anil.flightticketbookingsystem.models.Booking;
import dev.anil.flightticketbookingsystem.models.enums.BookingStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class BookingResponseDTO {

    private String number;
    private float amount;
    private BookingStatus bookingStatus;
    ResponseStatus status;
}
