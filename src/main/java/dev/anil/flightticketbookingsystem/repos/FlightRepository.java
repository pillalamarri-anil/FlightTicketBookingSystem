package dev.anil.flightticketbookingsystem.repos;

import dev.anil.flightticketbookingsystem.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
