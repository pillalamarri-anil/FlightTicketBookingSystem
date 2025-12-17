package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.FlightStatus;

import java.util.List;

@Getter
@Setter

@Entity
public class AirCraft extends BaseModel {

    private String number;
    private String make;
    private String model;
    private int  makeYear;

    @ManyToOne
    private Operator operator;

    @OneToMany(mappedBy = "airCraft", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Seat> seatList;

    @Enumerated(EnumType.ORDINAL)
    private FlightStatus isGrounded;
}
