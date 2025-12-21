package dev.anil.flightticketbookingsystem.models.UserModels;

import dev.anil.flightticketbookingsystem.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Role extends BaseModel {

    String roleName;
}
