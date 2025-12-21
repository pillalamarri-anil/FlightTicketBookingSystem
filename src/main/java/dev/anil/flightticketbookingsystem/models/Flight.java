package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter

@Entity
@Table(indexes = @Index(columnList = "id"))
public class Flight extends BaseModel {

    @Column(nullable = false)
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

    @Column(nullable = false)
    LocalDateTime departureTime;

    @Column(nullable = false)
    LocalDateTime arrivalTime;

    float minPrice;

    String currency;
}
