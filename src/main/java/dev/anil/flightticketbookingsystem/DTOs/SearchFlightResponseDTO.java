package dev.anil.flightticketbookingsystem.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class SearchFlightResponseDTO {

    List<FlightDTO> flights;
}
