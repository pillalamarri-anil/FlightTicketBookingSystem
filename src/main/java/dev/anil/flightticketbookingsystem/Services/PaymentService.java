package dev.anil.flightticketbookingsystem.Services;

import dev.anil.flightticketbookingsystem.models.enums.PaymentStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentStatus pay(float amount) {
        return PaymentStatus.CONFIRMED;
    }
}
