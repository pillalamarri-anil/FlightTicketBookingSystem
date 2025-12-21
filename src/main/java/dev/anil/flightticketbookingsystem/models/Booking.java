package dev.anil.flightticketbookingsystem.models;

import dev.anil.flightticketbookingsystem.models.UserModels.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.BookingStatus;

import java.util.List;

@Getter
@Setter

@Entity
public class Booking extends BaseModel {

    @Column(nullable = false)
    private String number;

    @ManyToOne
    private User user;

    @OneToOne
    private Payment payment;

    private float amount;

    @OneToMany( mappedBy = "booking", cascade = CascadeType.PERSIST)
    private List<Flight_Seat> flight_seats; // there can be cancellation also

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus status;
}
