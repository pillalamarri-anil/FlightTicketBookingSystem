package dev.anil.flightticketbookingsystem.models;

import dev.anil.flightticketbookingsystem.models.enums.SeatPosition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.SeatType;

import java.util.List;

@Getter
@Setter

@Entity
public class Seat extends BaseModel {

    @Column(nullable = false)
    private String seatNumber;

    @Enumerated(EnumType.ORDINAL)
    private SeatType seatType;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight_Seat> flightSeatList;

    @Enumerated(EnumType.ORDINAL)
    private SeatPosition seatPosition;

    @ManyToOne
    AirCraft airCraft;
}
