package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class City extends BaseModel {

    private String name;
    private String country;
    private String state;

    @Column(nullable = false)
    private String code;
}
