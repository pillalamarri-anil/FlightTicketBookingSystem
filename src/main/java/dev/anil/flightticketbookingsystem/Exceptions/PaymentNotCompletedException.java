package dev.anil.flightticketbookingsystem.Exceptions;

public class PaymentNotCompletedException extends Exception{
    public PaymentNotCompletedException(String message){
        super(message);
    }
}
