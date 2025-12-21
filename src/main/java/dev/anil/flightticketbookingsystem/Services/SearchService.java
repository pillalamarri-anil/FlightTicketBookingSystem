package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.DependentServiceException;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidCityExeption;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidDateException;
import dev.anil.flightticketbookingsystem.models.Flight;

import java.time.LocalDateTime;
import java.util.Date;

public interface SearchService {

    public Flight[] searchFlights(String sourceCityCode, String destinationCityCode, LocalDateTime date)
            throws InvalidCityExeption, InvalidDateException, DependentServiceException;


}
