package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.Exceptions.DependentServiceException;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidCityExeption;
import dev.anil.flightticketbookingsystem.Exceptions.InvalidDateException;
import dev.anil.flightticketbookingsystem.InventoryProvider.InventoryAdapter;
import dev.anil.flightticketbookingsystem.models.City;
import dev.anil.flightticketbookingsystem.models.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Executable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService{

    private InventoryAdapter inventoryAdapter;

    @Autowired
    public SearchServiceImpl(InventoryAdapter inventoryAdapter) {
        this.inventoryAdapter = inventoryAdapter;
    }

    public Flight[] searchFlights(String sourceCityCode, String destinationCityCode, LocalDateTime date)
            throws InvalidCityExeption, InvalidDateException, DependentServiceException {
        // validate city Code
        if (sourceCityCode == null || destinationCityCode == null
                || sourceCityCode.length() != 3 || destinationCityCode.length() != 3)
            throw new InvalidCityExeption("Source or Destination city code is invalid");

        if (date.isBefore(LocalDateTime.now()))
            throw new InvalidDateException("Date is invalid");

        try {
            City sourceCity = new City();
            sourceCity.setCode(sourceCityCode);
            City destinationCity = new City();
            destinationCity.setCode(destinationCityCode);

            return inventoryAdapter.getFlights(sourceCity, destinationCity, date);
        } catch (Exception e) {
            throw new DependentServiceException(e.getMessage());
        }
    }
}
