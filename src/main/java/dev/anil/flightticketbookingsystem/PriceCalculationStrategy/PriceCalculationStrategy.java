package dev.anil.flightticketbookingsystem.PriceCalculationStrategy;

import dev.anil.flightticketbookingsystem.models.Flight_Seat;

public interface PriceCalculationStrategy {

    float CalculatePrice(Flight_Seat showSeat);
}
