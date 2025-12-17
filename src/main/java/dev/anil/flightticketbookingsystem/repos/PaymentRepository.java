package dev.anil.flightticketbookingsystem.repos;

import dev.anil.flightticketbookingsystem.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
