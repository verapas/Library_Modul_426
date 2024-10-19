package ch.csbe.bibliotheksapp_modul426.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String author;

    private String isbn;

    private boolean available;

//    private Integer borrowedBy; // Nullable, kann die ID des Benutzers enthalten
//
//    private String borrowedDate; // FORMAT: YYYY-MM-DD
//
//    private String returnDate; // FORMAT: YYYY-MM-DD

    // Ein Buch kann in mehreren Ausleihen vorkommen
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Loan> loans;  // Liste der Loans eines Buches

}