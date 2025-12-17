package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.PriceCalculationStrategy.PriceCalculationStrategy;
import dev.anil.flightticketbookingsystem.models.Flight_Seat;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculationService {

    private final StringHttpMessageConverter stringHttpMessageConverter;
    private PriceCalculationStrategy strategy;

    public PriceCalculationService(StringHttpMessageConverter stringHttpMessageConverter) {
        this.stringHttpMessageConverter = stringHttpMessageConverter;
    }

    public  float calculcatePrice(List<Flight_Seat> flightSeats){

        float amount = 0L;
        for(Flight_Seat showSeat:  flightSeats){
            amount += strategy.CalculatePrice(showSeat);
        }
        return amount;
    }
}
