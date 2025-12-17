package dev.anil.flightticketbookingsystem.PriceCalculationStrategy;

import dev.anil.flightticketbookingsystem.models.Flight_Seat;

public class DynamicPricingStrategy implements PriceCalculationStrategy {
    @Override
    public float CalculatePrice(Flight_Seat showSeat) {
        return 100000;  // dummy implementation
    }
}
