package dev.anil.flightticketbookingsystem.Adivices;

import dev.anil.flightticketbookingsystem.Exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({InvalidCredentialsException.class, InvalidDateException.class,
            InvalidFlightException.class, InvalidUserException.class, UserNotFoundException.class,
    UserAlreadyExistsExeption.class, PaymentNotCompletedException.class, SeatNotAvailableException.class} )
    public ResponseEntity<String> handleInvalidInputException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
