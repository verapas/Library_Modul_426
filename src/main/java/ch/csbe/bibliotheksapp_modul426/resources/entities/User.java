package ch.csbe.bibliotheksapp_modul426.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String password;

    private String role;

    // Ein User kann mehrere Ausleihen haben (loans)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Loan> loans;

}