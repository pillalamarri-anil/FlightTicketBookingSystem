package dev.anil.flightticketbookingsystem.Controllers;

import dev.anil.flightticketbookingsystem.DTOs.BookingRequestDTO;
import dev.anil.flightticketbookingsystem.DTOs.BookingResponseDTO;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidFlightException;
import dev.anil.flightticketbookingsystem.Exceptions.PaymentNotCompletedException;
import dev.anil.flightticketbookingsystem.Exceptions.SeatNotAvailableException;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.Services.BookingService;
import dev.anil.flightticketbookingsystem.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flightBooking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    ResponseEntity<BookingResponseDTO> BookFlightTicket(@RequestBody BookingRequestDTO bookingRequestDTO) throws PaymentNotCompletedException,
            UserNotFoundException, InvalidFlightException, SeatNotAvailableException {

        Booking booking = bookingService.book(bookingRequestDTO.getUserId(), bookingRequestDTO.getFlightId(), bookingRequestDTO.getFlightSeatIds());

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO();
        bookingResponseDTO.setNumber(booking.getNumber());
        bookingResponseDTO.setAmount(booking.getAmount());
        bookingResponseDTO.setBookingStatus(booking.getStatus());
        return new ResponseEntity<>(bookingResponseDTO, HttpStatus.OK);
    }

}
