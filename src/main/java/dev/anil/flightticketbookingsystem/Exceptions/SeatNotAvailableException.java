package dev.anil.flightticketbookingsystem.Exceptions;

public class SeatNotAvailableException extends Exception{
    public SeatNotAvailableException(String message) {
        super(message);
    }
}
