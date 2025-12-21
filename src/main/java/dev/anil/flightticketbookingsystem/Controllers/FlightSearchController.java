package dev.anil.flightticketbookingsystem.Controllers;

import dev.anil.flightticketbookingsystem.DTOs.FlightDTO;
import dev.anil.flightticketbookingsystem.DTOs.SearchFlightResponseDTO;
import dev.anil.flightticketbookingsystem.Exceptions.DependentServiceException;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidCityExeption;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidDateException;
import dev.anil.flightticketbookingsystem.Services.SearchService;
import dev.anil.flightticketbookingsystem.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.DatabindException;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("flights/")
public class FlightSearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("search")
    public ResponseEntity<SearchFlightResponseDTO> searchFlight(@RequestParam String sourceCityCode, @RequestParam String destinationCityCode, @RequestParam String date)
    throws InvalidCityExeption, InvalidDateException, DependentServiceException {

        SearchFlightResponseDTO response = new SearchFlightResponseDTO();

        List<FlightDTO> flightDTOList = new ArrayList<FlightDTO>();
        Flight[] flights = searchService.searchFlights(sourceCityCode, destinationCityCode, LocalDateTime.parse(date));
        for (Flight flight : flights) {
            FlightDTO flightDTO = new FlightDTO();
            flightDTO.setFlightNumber(flight.getFlightNumber());
            flightDTO.setSource(flight.getSource().getCode());
            flightDTO.setDestination(flight.getDestination().getCode());
            flightDTO.setDepartureDateTime( flight.getDepartureTime().toString());
            flightDTO.setArrivalDateTime(flight.getArrivalTime().toString());
            flightDTO.setMinPrice(flight.getMinPrice());
            flightDTO.setCurrency(flight.getCurrency());
            flightDTOList.add(flightDTO);
        }
        response.setFlights(flightDTOList);
        return ResponseEntity.ok(response);
    }
}
