package dev.anil.flightticketbookingsystem.InventoryProvider;

import dev.anil.flightticketbookingsystem.models.*;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;

public interface InventoryAdapter {

    Flight[] getFlights(City source, City destination, Date departureDate);
    Flight_Seat[] ShowSeats(Flight flight);

    Booking[] BookTicket(User user, List<Flight_Seat> flightSeatList);
}
