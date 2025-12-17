package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
public class Flight extends BaseModel {

    private String flightNumber;

    @ManyToOne
    private AirCraft airCraft;

    @OneToMany( mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Flight_Seat> seats;

    @ManyToOne
    private City source;

    @ManyToOne
    private City destination;

    @ManyToOne
    private Operator operator;

    long departureTime;

    long arrivalTime;

    long minPrice;
}
