package ch.csbe.bibliotheksapp_modul426.resources.entities;

import lombok.Data;

@Data
public class Loan {
    private int id;
    private int userId; // Fremdschlüssel
    private int bookId; // Fremdschlüssel
    private String loanDate; // FORMAT: YYYY-MM-DD
    private String returnDate; // FORMAT: YYYY-MM-DD
    private String status; // ENUM: "ausgeliehen", "zurückgegeben"
}