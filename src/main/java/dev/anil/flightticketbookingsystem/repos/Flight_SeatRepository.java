package dev.anil.flightticketbookingsystem.repos;

import dev.anil.flightticketbookingsystem.models.Flight_Seat;
import dev.anil.flightticketbookingsystem.models.enums.FlightSeatStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Flight_SeatRepository extends JpaRepository<Flight_Seat, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select fs from Flight_Seat fs where fs.id IN :ids and fs.status = :status")
    List<Flight_Seat> findAllByIdAndStatus(List<Long> ids, FlightSeatStatus status);

    Flight_Seat save(Flight_Seat flight_seat);

    @Modifying
    @Query("update Flight_Seat fs set fs.status = :status where fs IN :flightSeats")
    List<Flight_Seat> updateShowSeats( List<Flight_Seat> flightSeats, FlightSeatStatus status);

    Flight_Seat findById(long id);
}
