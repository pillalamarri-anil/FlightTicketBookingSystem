package dev.anil.flightticketbookingsystem.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
public class Operator extends BaseModel{

    String name;

    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AirCraft> airCrafts;

    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Flight> flights;

}
