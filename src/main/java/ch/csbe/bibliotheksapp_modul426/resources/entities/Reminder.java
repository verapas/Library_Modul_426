package ch.csbe.bibliotheksapp_modul426.resources.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int loanId; // Fremdschlüssel

    private boolean emailSent;

    // Mehrere reminders können zu einer Ausleihe gehören
    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;
}
