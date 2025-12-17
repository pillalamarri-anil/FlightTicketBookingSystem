package dev.anil.flightticketbookingsystem.Controllers;

import dev.anil.flightticketbookingsystem.DTOs.BookingRequestDTO;
import dev.anil.flightticketbookingsystem.DTOs.BookingResponseDTO;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidFlightException;
import dev.anil.flightticketbookingsystem.Exceptions.PaymentNotCompletedException;
import dev.anil.flightticketbookingsystem.Exceptions.SeatNotAvailableException;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.Services.BookingService;
import dev.anil.flightticketbookingsystem.models.Booking;
import dev.anil.flightticketbookingsystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    ResponseEntity<Booking> BookFlightTicket(BookingRequestDTO bookingRequestDTO) throws PaymentNotCompletedException,
            UserNotFoundException, InvalidFlightException, SeatNotAvailableException {

        return new ResponseEntity<Booking>(bookingService.book(bookingRequestDTO.getUserId(), bookingRequestDTO.getFlightId(), bookingRequestDTO.getFlightSeatIds()),
                HttpStatus.OK);
    }

}
