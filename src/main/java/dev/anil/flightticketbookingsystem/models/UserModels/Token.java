package dev.anil.flightticketbookingsystem.models.UserModels;

import dev.anil.flightticketbookingsystem.models.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

@Entity
public class Token extends BaseModel {

    private String tokenValue;
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
