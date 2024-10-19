package ch.csbe.bibliotheksapp_modul426.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Loan {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    private String loanDate; // FORMAT: YYYY-MM-DD

    private String returnDate; // FORMAT: YYYY-MM-DD

    private String status; // ENUM: "ausgeliehen", "zurückgegeben"


    // Mehrere Loans gehören zu einem User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Mehrere Loans gehören zu einem Buch
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Eine Ausleihe kann mehrere Erinnerungen (reminders) haben
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<Reminder> reminders;  // Liste der Erinnerungen für eine Loan

}