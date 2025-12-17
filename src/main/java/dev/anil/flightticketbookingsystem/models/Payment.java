package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import dev.anil.flightticketbookingsystem.models.enums.PaymentStatus;
import dev.anil.flightticketbookingsystem.models.enums.PaymentType;

@Getter
@Setter

@Entity
public class Payment extends BaseModel{

    private long paymentId;

    @Enumerated(EnumType.ORDINAL)
    PaymentType paymentType;

    @Enumerated(EnumType.ORDINAL)
    PaymentStatus paymentStatus;

    float amount;

    @OneToOne(mappedBy = "payment")
    Booking booking;
}
