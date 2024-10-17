package ch.csbe.bibliotheksapp_modul426.resources.entities;

import lombok.Data;

@Data
public class Reminder {
    private int id;
    private int loanId; // Fremdschlüssel
    private boolean emailSent;
}
