package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.PriceCalculationStrategy.PriceCalculationStrategy;
import dev.anil.flightticketbookingsystem.models.Flight_Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculationService {

    private PriceCalculationStrategy strategy;

    @Autowired
    public PriceCalculationService(PriceCalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public  float calculcatePrice(List<Flight_Seat> flightSeats){

        float amount = 0L;
        for(Flight_Seat showSeat:  flightSeats){
            amount += strategy.CalculatePrice(showSeat);
        }
        return amount;
    }
}
