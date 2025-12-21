package dev.anil.flightticketbookingsystem.InventoryProvider;

import dev.anil.flightticketbookingsystem.models.*;
import dev.anil.flightticketbookingsystem.models.UserModels.User;

import java.time.LocalDateTime;
import java.util.List;

public class SabreInventoryAdapter implements InventoryAdapter{

    @Override
    public Flight[] getFlights(City source, City destination, LocalDateTime departureDate) {
        return new Flight[0];
    }

    @Override
    public Flight_Seat[] ShowSeats(Flight flight) {
        return new Flight_Seat[0];
    }

    @Override
    public Booking[] BookTicket(User user, List<Flight_Seat> flightSeatList) {
        return new Booking[0];
    }
}
