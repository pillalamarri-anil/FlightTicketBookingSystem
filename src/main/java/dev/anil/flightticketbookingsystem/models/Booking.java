package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.BookingStatus;

import java.util.List;

@Getter
@Setter

@Entity
public class Booking extends BaseModel {

    private String number;

    @ManyToOne
    private User user;

    @OneToOne
    private Payment payment;

    private float amount;

    @OneToMany( mappedBy = "booking", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Flight_Seat> flight_seats; // there can be cancellation also

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus status;
}
