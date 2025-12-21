package dev.anil.flightticketbookingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightDTO {

    private String departureDateTime;
    private String flightNumber;
    private String source;
    private String destination;
    private String arrivalDateTime;
    private float minPrice;
    private String currency;
}
