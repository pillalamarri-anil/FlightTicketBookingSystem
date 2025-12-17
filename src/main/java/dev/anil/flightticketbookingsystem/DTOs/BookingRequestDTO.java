package dev.anil.flightticketbookingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingRequestDTO {
    long UserId;
    long FlightId;
    List<Long> flightSeatIds;
}
