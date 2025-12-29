package dev.anil.flightticketbookingsystem.Services;


import dev.anil.flightticketbookingsystem.Exceptions.InvalidFlightException;
import dev.anil.flightticketbookingsystem.Exceptions.PaymentNotCompletedException;
import dev.anil.flightticketbookingsystem.Exceptions.SeatNotAvailableException;
import dev.anil.flightticketbookingsystem.Exceptions.UserNotFoundException;
import dev.anil.flightticketbookingsystem.models.Booking;

import java.util.List;

public interface BookingService {

    public Booking book(long userId, long flightId, List<Long> flightSeatIds)
            throws UserNotFoundException, InvalidFlightException, SeatNotAvailableException, PaymentNotCompletedException;
}