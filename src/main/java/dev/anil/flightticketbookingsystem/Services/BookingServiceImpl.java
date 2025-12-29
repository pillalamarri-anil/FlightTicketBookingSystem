package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.InvalidFlightException;
import dev.anil.flightticketbookingsystem.Exceptions.PaymentNotCompletedException;
import dev.anil.flightticketbookingsystem.Exceptions.SeatNotAvailableException;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.models.Booking;
import dev.anil.flightticketbookingsystem.models.Flight;
import dev.anil.flightticketbookingsystem.models.Flight_Seat;
import dev.anil.flightticketbookingsystem.models.UserModels.User;
import dev.anil.flightticketbookingsystem.models.enums.BookingStatus;
import dev.anil.flightticketbookingsystem.models.enums.FlightSeatStatus;
import dev.anil.flightticketbookingsystem.models.enums.PaymentStatus;
import dev.anil.flightticketbookingsystem.repos.BookingRepository;
import dev.anil.flightticketbookingsystem.repos.FlightRepository;
import dev.anil.flightticketbookingsystem.repos.Flight_SeatRepository;
import dev.anil.flightticketbookingsystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private UserRepository userRepository;
    private BookingRepository bookingRepository;
    private PriceCalculationService priceCalculationService;
    private PaymentService paymentService;
    private FlightRepository flightRepository;
    private Flight_SeatRepository flight_SeatRepository;

    @Autowired
    public BookingServiceImpl(UserRepository userRepository, FlightRepository flightRepository,
                          Flight_SeatRepository flight_SeatRepository, BookingRepository bookingRepository,
                          PriceCalculationService priceCalculationService,
                          PaymentService paymentService) {
        this.userRepository = userRepository;
        this.flightRepository = flightRepository;
        this.flight_SeatRepository = flight_SeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculationService = priceCalculationService;
        this.paymentService = paymentService;
    }

    @Transactional(isolation= Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Booking book(long userId, long flightId, List<Long> flightSeatIds)
            throws UserNotFoundException, InvalidFlightException, SeatNotAvailableException, PaymentNotCompletedException
    {
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User Not Found"));
        Flight flight = flightRepository.findById(flightId).orElseThrow(()->new InvalidFlightException("Flight Not Found"));
        List<Flight_Seat> flightSeats = flight_SeatRepository.findAllByIdAndStatus(flightSeatIds, FlightSeatStatus.AVAILABLE);
        if(flightSeats.size() != flightSeatIds.size())
            throw new SeatNotAvailableException("One or more Seats Not available");

        flight_SeatRepository.updateShowSeats(flightSeats, FlightSeatStatus.BLOCKED);

        float amount = priceCalculationService.calculcatePrice(flightSeats);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setAmount(amount);
        booking.setFlight_seats(flightSeats);
        booking.setStatus(BookingStatus.PENDING);

        // make payment
        PaymentStatus paymentStatus = paymentService.pay(amount);
        if(paymentStatus != PaymentStatus.CONFIRMED)
            throw new PaymentNotCompletedException("Payment Not Completed");

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setNumber("BOOKING-"+System.currentTimeMillis());
        bookingRepository.save(booking);
        return booking;
    }

}