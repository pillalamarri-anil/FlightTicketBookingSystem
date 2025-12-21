package dev.anil.flightticketbookingsystem.InventoryProvider;

import dev.anil.flightticketbookingsystem.models.*;
import dev.anil.flightticketbookingsystem.models.UserModels.User;

import java.time.LocalDateTime;
import java.util.List;

public interface InventoryAdapter {

    Flight[] getFlights(City source, City destination, LocalDateTime departureDate);
    Flight_Seat[] ShowSeats(Flight flight);

    Booking[] BookTicket(User user, List<Flight_Seat> flightSeatList);
}
