package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.PaymentStatus;
import dev.anil.flightticketbookingsystem.models.enums.PaymentType;

@Getter
@Setter

@Entity
public class Payment extends BaseModel{

    @Column(nullable = false)
    private long paymentId;

    @Enumerated(EnumType.ORDINAL)
    PaymentType paymentType;

    @Enumerated(EnumType.ORDINAL)
    PaymentStatus paymentStatus;

    float amount;

    @OneToOne(mappedBy = "payment")
    Booking booking;
}
