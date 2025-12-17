package dev.anil.flightticketbookingsystem;

import dev.anil.flightticketbookingsystem.Services.SearchServiceImpl;
import dev.anil.flightticketbookingsystem.models.City;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

@EnableJpaAuditing
@SpringBootApplication
public class FlightTicketBookingSystemApplication {

    public static void main(String[] args) {

      SpringApplication.run(FlightTicketBookingSystemApplication.class, args);
    }
}
