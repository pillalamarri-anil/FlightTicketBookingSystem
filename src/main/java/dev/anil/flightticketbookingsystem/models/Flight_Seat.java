package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.FlightSeatStatus;

@Getter
@Setter

@Entity
@Table(indexes = @Index(columnList = "id"))
public class Flight_Seat extends BaseModel{

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Flight flight;

    private float price;

    @ManyToOne
    private Booking booking;

    @Enumerated(EnumType.ORDINAL)
    private FlightSeatStatus status;
}
