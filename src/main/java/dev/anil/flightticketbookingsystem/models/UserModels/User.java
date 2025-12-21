package dev.anil.flightticketbookingsystem.models.UserModels;


import dev.anil.flightticketbookingsystem.models.BaseModel;
import dev.anil.flightticketbookingsystem.models.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(indexes = @Index(columnList = "email"))
public class User extends BaseModel {

    String name;

    @Column(nullable = false)
    String email;
    String mobile;
    String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Booking> bookingList;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Token> tokens;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Role> roles;
}
